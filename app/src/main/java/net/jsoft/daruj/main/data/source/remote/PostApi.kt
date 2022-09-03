package net.jsoft.daruj.main.data.source.remote

import android.net.Uri
import net.jsoft.daruj.main.domain.model.Post

interface PostApi {
    suspend fun getPosts(vararg ids: String): List<Post>

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
    suspend fun getMyPosts(reset: Boolean): List<Post>
    suspend fun getMyDonations(reset: Boolean): List<Post>
}