package net.jsoft.daruj.main.data.source.remote

import android.app.Application
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import net.jsoft.daruj.common.domain.repository.UserRepository
import net.jsoft.daruj.common.utils.awaitOrNull
import net.jsoft.daruj.common.utils.compressToByteArray
import net.jsoft.daruj.common.utils.getBitmap
import net.jsoft.daruj.main.data.source.remote.dto.PostDto
import net.jsoft.daruj.main.domain.model.Post
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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

    private var recommendedPosts = FirebaseRecommendedPostsPaginator(firestore, storage, userRepository)
    private var savedPosts = FirebaseSavedPostsPaginator(userRepository, this)

    override suspend fun getPost(id: String): Post = coroutineScope {
        val deferredLocalUser = async { userRepository.getLocalUser() }
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
            isSaved = deferredLocalUser.await().mutable.savedPosts.contains(postDto.id!!)
        )
    }

    override suspend fun createPost(post: Post.Mutable): String {
        val postDocument = posts
            .add(post)
            .await()

        postDocument
            .set(
                mapOf(
                    PostDto::userId.name to auth.currentUser!!.uid
                ),
                SetOptions.merge()
            )
            .await()

        return postDocument.id
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
        if (reset) recommendedPosts.reset()
        return recommendedPosts.getNextPage()
    }

    override suspend fun getSavedPosts(reset: Boolean): List<Post> {
        if (reset) savedPosts.reset()
        return savedPosts.getNextPage()
    }

    companion object {
        const val POSTS = "posts"
    }
}