package net.jsoft.daruj.main.data.source.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import net.jsoft.daruj.common.domain.repository.UserRepository
import net.jsoft.daruj.common.misc.Paginator
import net.jsoft.daruj.common.utils.awaitOrNull
import net.jsoft.daruj.main.data.source.remote.dto.PostDto
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.repository.PostRepository

class FirebaseRecommendedPostsPaginator(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,

    private val userRepository: UserRepository
) : Paginator<Post> {
    private var lastPostSnapshot: DocumentSnapshot? = null

    private val posts: CollectionReference
        get() = firestore.collection(FirebasePostApi.POSTS)

    private val postsStorage: StorageReference
        get() = storage.reference.child(FirebasePostApi.POSTS)

    override suspend fun getNextPage(): List<Post> = coroutineScope {
        val postSnapshots = posts
            .orderBy(Post.Immutable::timestamp.name, Query.Direction.DESCENDING)
            .limit(PostRepository.POSTS_PER_PAGE.toLong()).apply {
                if (lastPostSnapshot != null) startAfter(lastPostSnapshot)
            }.get().await()

        val postDtos = postSnapshots.toObjects<PostDto>()

        val deferredPictureUris = postDtos.map { postDto ->
            postsStorage
                .child("${postDto.id!!}.png")
                .downloadUrl
                .asDeferred()
        }

        val deferredUsers = postDtos.map { postDto ->
            async { userRepository.getUser(postDto.userId!!) }
        }

        val localUser = userRepository.getLocalUser()

        lastPostSnapshot = postSnapshots.lastOrNull()
        postDtos.mapIndexed { index, postDto ->
            postDto.getModel(
                user = deferredUsers[index].await(),
                pictureUri = deferredPictureUris[index].awaitOrNull(),
                isSaved = localUser.mutable.savedPosts.contains(postDto.id)
            )
        }
    }

    override suspend fun reset() {
        lastPostSnapshot = null
    }
}