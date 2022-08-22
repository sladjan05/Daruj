package net.jsoft.daruj.main.data.source.remote

import android.net.Uri
import net.jsoft.daruj.main.domain.model.Post

interface PostApi {
    suspend fun getPost(id: String): Post

    suspend fun createPost(post: Post.Mutable): String
    suspend fun updatePost(
        postId: String,
        post: Post.Mutable
    )

    suspend fun updatePostPicture(
        postId: String,
        pictureUri: Uri
    )

    suspend fun getRecommendedPosts(reset: Boolean): List<Post>
    suspend fun getSavedPosts(reset: Boolean): List<Post>
}