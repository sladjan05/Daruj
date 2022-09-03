package net.jsoft.daruj.main.data.repository

import android.net.Uri
import kotlinx.coroutines.withContext
import net.jsoft.daruj.common.misc.DispatcherProvider
import net.jsoft.daruj.main.data.source.remote.PostApi
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.repository.PostRepository

class PostRepositoryImpl(
    private val postApi: PostApi,
    private val dispatcherProvider: DispatcherProvider
) : PostRepository {

    override suspend fun getPosts(vararg ids: String): List<Post> =
        withContext(dispatcherProvider.io) {
            postApi.getPosts(*ids)
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

    override suspend fun getMyPosts(reset: Boolean): List<Post> =
        withContext(dispatcherProvider.io) {
            postApi.getMyPosts(reset)
        }
}