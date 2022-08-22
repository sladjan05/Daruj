package net.jsoft.daruj.main.data.repository

import android.net.Uri
import kotlinx.coroutines.withContext
import net.jsoft.daruj.common.misc.DispatcherProvider
import net.jsoft.daruj.main.data.source.remote.PostApi
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi,

    private val dispatcherProvider: DispatcherProvider
) : PostRepository {

    override suspend fun getPost(id: String): Post =
        withContext(dispatcherProvider.io) {
            postApi.getPost(id)
        }

    override suspend fun createPost(post: Post.Mutable): String =
        withContext(dispatcherProvider.io) {
            postApi.createPost(post)
        }

    override suspend fun updatePost(
        postId: String,
        post: Post.Mutable
    ) = withContext(dispatcherProvider.io) {
        postApi.updatePost(postId, post)
    }

    override suspend fun updatePostPicture(
        postId: String,
        pictureUri: Uri
    ) = withContext(dispatcherProvider.io) {
        postApi.updatePostPicture(postId, pictureUri)
    }

    override suspend fun getRecommendedPosts(reset: Boolean): List<Post> =
        withContext(dispatcherProvider.io) {
            postApi.getRecommendedPosts(reset)
        }

    override suspend fun getSavedPosts(reset: Boolean): List<Post> =
        withContext(dispatcherProvider.io) {
            postApi.getSavedPosts(reset)
        }
}