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
import com.google.gson.Gson
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import net.jsoft.daruj.common.domain.model.User
import net.jsoft.daruj.common.domain.repository.UserRepository
import net.jsoft.daruj.common.utils.asMap
import net.jsoft.daruj.common.utils.awaitOrNull
import net.jsoft.daruj.common.utils.compressToByteArray
import net.jsoft.daruj.common.utils.getBitmap
import net.jsoft.daruj.main.data.source.remote.dto.PostDto
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.repository.PostRepository
import java.lang.Integer.min
import javax.inject.Inject

@ViewModelScoped
class FirebasePostApi @Inject constructor(
    private val application: Application,

    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,

    private val userRepository: UserRepository
) : PostApi {

    private val posts: CollectionReference
        get() = firestore.collection(POSTS)

    private val postsStorage: StorageReference
        get() = storage.reference.child(POSTS)

    private var lastRecommendedPostSnapshot: DocumentSnapshot? = null
    private val lastSavedPostIds = mutableListOf<String>()
    private var lastMyPostSnapshot: DocumentSnapshot? = null

    override suspend fun getPosts(vararg ids: String): List<Post> = coroutineScope {
        if (ids.isEmpty()) return@coroutineScope emptyList<Post>()
        val deferredLocalUser = async { userRepository.getLocalUser() }

        ids.map { id ->
            val deferredPostSnapshot = posts.document(id).get().asDeferred()
            val deferredPictureUri = postsStorage
                .child("$id.png")
                .downloadUrl
                .asDeferred()

            val postDto = deferredPostSnapshot.await().toObject<PostDto>()!!
            val deferredUser = async { userRepository.getUser(postDto.userId!!) }

            postDto.getModel(
                user = deferredUser.await(),
                pictureUri = deferredPictureUri.awaitOrNull(),
                isSaved = deferredLocalUser.await().immutable.savedPosts.contains(postDto.id!!)
            )
        }
    }

    override suspend fun createPost(post: Post.Mutable): String {
        val postMerge = post.asMap(
            PostDto::userId.name to auth.currentUser!!.uid
        )

        val postDocument = posts.add(postMerge).await()

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

    override suspend fun updatePostPicture(
        postId: String,
        pictureUri: Uri
    ) {
        val bitmap = application.getBitmap(
            uri = pictureUri,
            width = 500,
            height = 500
        )

        val compressed = bitmap?.compressToByteArray(50)

        val metadata = StorageMetadata.Builder()
            .setContentType("image/png")
            .build()

        postsStorage.child("$postId.png").putBytes(compressed!!, metadata).await()
    }

    override suspend fun getRecommendedPosts(reset: Boolean): List<Post> {
        if (reset) lastRecommendedPostSnapshot = null

        val (posts, lastSnapshot) = getPosts(posts, lastRecommendedPostSnapshot)
        lastRecommendedPostSnapshot = lastSnapshot

        return posts
    }

    override suspend fun getSavedPosts(reset: Boolean): List<Post> = coroutineScope {
        if (reset) lastSavedPostIds.clear()

        val localUser = userRepository.getLocalUser()
        val savedPostIds = localUser.immutable.savedPosts.toMutableList()

        var startIndex = 0
        for ((index, id) in savedPostIds.reversed().withIndex()) {
            if (lastSavedPostIds.contains(id)) {
                startIndex = savedPostIds.size - index - 1
                break
            }
        }

        val endIndex = min(startIndex + PostRepository.POSTS_PER_PAGE, savedPostIds.size)
        val postIds = savedPostIds.subList(startIndex, endIndex)

        getPosts(*postIds.toTypedArray())
    }

    override suspend fun getMyPosts(reset: Boolean): List<Post> {
        if (reset) lastMyPostSnapshot = null

        val query = posts.whereEqualTo(PostDto::userId.name, auth.currentUser!!.uid)

        val (posts, lastSnapshot) = getPosts(query, lastMyPostSnapshot)
        lastMyPostSnapshot = lastSnapshot

        return posts
    }

    override suspend fun getMyDonations(reset: Boolean): List<Post> {
        TODO()
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
            .limit(PostRepository.POSTS_PER_PAGE.toLong())
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

        // Optimization: There might be multiple posts from one creator
        val deferredUsers = mutableMapOf<String, Deferred<User>>()
        for (postDto in postDtos) {
            val userId = postDto.userId!!

            if (userId !in deferredUsers) {
                deferredUsers[userId] = async { userRepository.getUser(userId) }
            }
        }

        val localUser = userRepository.getLocalUser()

        postDtos.mapIndexed { index, postDto ->
            val userId = postDto.userId!!

            val user = deferredUsers[userId]!!.await()
            val pictureUri = deferredPictureUris[index].awaitOrNull()
            val savedPosts = localUser.immutable.savedPosts

            postDto.getModel(
                user = user,
                pictureUri = pictureUri,
                isSaved = postDto.id in savedPosts
            )
        } to postSnapshots.lastOrNull()
    }

    companion object {
        const val POSTS = "posts"
    }
}