package net.jsoft.daruj.main.data.source.remote

import android.app.Application
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import net.jsoft.daruj.comment.data.remote.dto.CommentDto
import net.jsoft.daruj.comment.domain.model.Comment
import net.jsoft.daruj.common.data.source.remote.FirebaseUserApi
import net.jsoft.daruj.common.domain.model.User
import net.jsoft.daruj.common.util.*
import net.jsoft.daruj.donate_blood.data.source.remote.FirebaseReceiptApi
import net.jsoft.daruj.main.data.source.remote.dto.PostDto
import net.jsoft.daruj.main.domain.model.Donation
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.repository.PostRepository
import java.lang.Integer.min
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebasePostApi @Inject constructor(
    private val application: Application,

    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,

    private val userApi: FirebaseUserApi,
    private val receiptApi: FirebaseReceiptApi
) : PostApi {

    private val posts: CollectionReference
        get() = firestore.collection(Posts)

    private val postsStorage: StorageReference
        get() = storage.reference.child(Posts)

    private val comments: CollectionReference
        get() = firestore.collection(Comments)

    private var lastRecommendedPostSnapshot: DocumentSnapshot? = null
    private val lastSavedPostIds = mutableListOf<String>()
    private var lastMyPostSnapshot: DocumentSnapshot? = null
    private val lastDonationsPostIds = mutableListOf<String>()

    private var lastSearchSnapshot: DocumentSnapshot? = null
    private var lastCriteria: String = ""

    override suspend fun getPosts(vararg ids: String): List<Post> = coroutineScope {
        if (ids.isEmpty()) return@coroutineScope emptyList<Post>()
        val deferredLocalUser = async { userApi.getLocalUser() }

        val deferredComments = ids.associateWith { id ->
            comments.whereEqualTo(CommentDto::postId.name, id).get().asDeferred()
        }

        val posts = ids.map { id ->
            val deferredPostSnapshot = posts.document(id).get().asDeferred()
            val deferredPictureUri = postsStorage
                .child("$id.png")
                .downloadUrl
                .asDeferred()

            val postDto = deferredPostSnapshot.await().toObject<PostDto>() ?: return@map null
            val deferredUser = async { userApi.getUser(postDto.userId!!) }
            val localUser = deferredLocalUser.await()

            val isMyPost = localUser.data.id == postDto.userId
            val deferredReceiptCount = async { if (isMyPost) receiptApi.getReceiptCount(id) else 0 }

            val blood = postDto.blood!!.getModel()

            postDto.getModel(
                user = deferredUser.await(),
                pictureUri = deferredPictureUri.awaitOrNull(),
                isMyPost = isMyPost,
                receiptCount = deferredReceiptCount.await(),
                isSaved = postDto.id!! in localUser.immutable.savedPosts,
                isBloodCompatible = localUser.mutable.blood.canDonateTo(blood),
                commentCount = deferredComments[id]!!.await().size()
            )
        }

        // Some posts may not exist, so it should be checked if there are any nulls in the list
        posts.filterNotNull()
    }

    override suspend fun createPost(post: Post.Mutable): String {
        val postWithUser = post.asMap(
            PostDto::userId.name to auth.currentUser!!.uid
        )

        val postDocument = posts.add(postWithUser).await()

        return postDocument!!.id
    }

    override suspend fun updatePost(
        postId: String,
        post: Post.Mutable
    ) {
        posts.document(postId)
            .set(post, SetOptions.merge())
            .await()
    }

    override suspend fun deletePost(postId: String) = coroutineScope {
        val firestoreJob = launch {
            posts.document(postId)
                .delete()
                .await()
        }

        val deferredStorage =
            postsStorage.child("$postId.png")
                .delete()
                .asDeferred()

        firestoreJob.join()
        deferredStorage.safeAwait()
    }

    override suspend fun updatePostPicture(
        postId: String,
        pictureUri: Uri
    ) {
        val bitmap = application.getBitmap(
            uri = pictureUri,
            width = 500,
            height = 500
        )

        val compressed = bitmap?.compressToByteArray()

        val metadata = StorageMetadata.Builder()
            .setContentType("image/png")
            .build()

        postsStorage.child("$postId.png")
            .putBytes(compressed!!, metadata)
            .await()
    }

    override suspend fun getRecommendedPosts(reset: Boolean): List<Post> {
        if (reset) lastRecommendedPostSnapshot = null

        val (posts, lastSnapshot) = getPosts(posts, lastRecommendedPostSnapshot)
        lastRecommendedPostSnapshot = lastSnapshot

        return posts
    }

    override suspend fun getSavedPosts(reset: Boolean): List<Post> {
        if (reset) lastSavedPostIds.clear()

        val localUser = userApi.getLocalUser()
        val savedPostIds = localUser.immutable.savedPosts.reversed()

        val startIndex = savedPostIds.indexOfLast { postId -> postId in lastSavedPostIds } + 1
        val endIndex = min(startIndex + PostRepository.PostsPerPage, savedPostIds.size)

        val postIds = savedPostIds.subList(startIndex, endIndex)
        lastSavedPostIds += postIds

        return getPosts(*postIds.toTypedArray())
    }

    override suspend fun getMyPosts(reset: Boolean): List<Post> {
        if (reset) lastMyPostSnapshot = null

        val query = posts.whereEqualTo(PostDto::userId.name, auth.currentUser!!.uid)

        val (posts, lastSnapshot) = getPosts(query, lastMyPostSnapshot)
        lastMyPostSnapshot = lastSnapshot

        return posts
    }

    override suspend fun searchPosts(criteria: String): List<Post> {
        if (criteria != lastCriteria) lastSearchSnapshot = null

        val query = posts.whereEqualTo(PostDto::searchCriteria.name, criteria.lowercase())

        val (posts, lastSnapshot) = getPosts(query, lastSearchSnapshot)
        lastSearchSnapshot = lastSnapshot

        return posts
    }

    override suspend fun getDonations(reset: Boolean): List<Donation> {
        if (reset) lastDonationsPostIds.clear()

        val localUser = userApi.getLocalUser()
        val donationRecords = localUser.immutable.donations.reversed()

        val startIndex = donationRecords.indexOfLast { record -> record.postId in lastDonationsPostIds } + 1
        val endIndex = min(startIndex + PostRepository.PostsPerPage, donationRecords.size)

        val currentDonationRecords = donationRecords.subList(startIndex, endIndex)
        val postIds = currentDonationRecords.map { record -> record.postId }
        lastDonationsPostIds += postIds

        val posts = getPosts(*postIds.toTypedArray())
        return posts.zip(currentDonationRecords) { post, record ->
            Donation(
                post = post,
                timestamp = record.timestamp
            )
        }
    }

    override suspend fun postComment(postId: String, comment: Comment.Mutable) {
        val localUser = userApi.getLocalUser()
        val commentWithUser = comment.asMap(
            CommentDto::postId.name to postId,
            CommentDto::userId.name to localUser.data.id
        )

        comments.add(commentWithUser).await()
    }

    override suspend fun getComment(id: String): Comment {
        val commentDto = comments
            .document(id)
            .get()
            .await()
            .toObject<CommentDto>()!!

        val user = userApi.getUser(commentDto.userId!!)
        return commentDto.getModel(user = user)
    }

    override suspend fun getComments(postId: String): List<Comment> = coroutineScope {
        val commentDtos = comments
            .whereEqualTo(CommentDto::postId.name, postId)
            .orderBy(Comment.Immutable::timestamp.name, Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects<CommentDto>()

        // Optimization: There might be multiple posts from one creator
        val deferredUsers = mutableMapOf<String, Deferred<User>>()
        for (commentDto in commentDtos) {
            val userId = commentDto.userId!!

            if (userId !in deferredUsers) {
                deferredUsers[userId] = async { userApi.getUser(userId) }
            }
        }

        commentDtos.map { commentDto ->
            val userId = commentDto.userId!!
            val user = deferredUsers[userId]!!.await()

            commentDto.getModel(user = user)
        }
    }

    private suspend fun getPosts(
        query: Query,
        lastSnapshot: DocumentSnapshot?
    ): Pair<List<Post>, DocumentSnapshot?> = coroutineScope {
        val postSnapshots = query
            .orderBy(Post.Immutable::timestamp.name, Query.Direction.DESCENDING)
            .let { query ->
                if (lastSnapshot != null) query.startAfter(lastSnapshot)
                else query
            }
            .limit(PostRepository.PostsPerPage.toLong())
            .get()
            .await()

        if (postSnapshots.isEmpty) return@coroutineScope emptyList<Post>() to lastSnapshot

        val postDtos = postSnapshots.toObjects<PostDto>()

        val deferredPictureUris = postDtos.map { postDto ->
            postsStorage
                .child("${postDto.id!!}.png")
                .downloadUrl
                .asDeferred()
        }

        val postIds = postDtos.map { postDto -> postDto.id }
        val deferredComments = postIds.associateWith { id ->
            comments.whereEqualTo(CommentDto::postId.name, id).get()
        }

        // Optimization: There might be multiple posts from one creator
        val deferredUsers = mutableMapOf<String, Deferred<User>>()
        for (postDto in postDtos) {
            val userId = postDto.userId!!

            if (userId !in deferredUsers) {
                deferredUsers[userId] = async { userApi.getUser(userId) }
            }
        }

        val localUser = userApi.getLocalUser()

        postDtos.mapIndexed { index, postDto ->
            val postId = postDto.id!!
            val userId = postDto.userId!!

            val user = deferredUsers[userId]!!.await()
            val pictureUri = deferredPictureUris[index].awaitOrNull()
            val savedPosts = localUser.immutable.savedPosts

            val isMyPost = localUser.data.id == postDto.userId
            val deferredReceiptCount = async { if (isMyPost) receiptApi.getReceiptCount(postId) else 0 }

            val blood = postDto.blood!!.getModel()

            postDto.getModel(
                user = user,
                pictureUri = pictureUri,
                isMyPost = isMyPost,
                receiptCount = deferredReceiptCount.await(),
                isSaved = postDto.id in savedPosts,
                isBloodCompatible = localUser.mutable.blood.canDonateTo(blood),
                commentCount = deferredComments[postId]!!.await().size()
            )
        } to postSnapshots.lastOrNull()
    }

    companion object {
        private const val Posts = "posts"
        private const val Comments = "comments"
    }
}