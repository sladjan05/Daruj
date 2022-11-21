package net.jsoft.daruj.main.domain.repository

import android.net.Uri
import net.jsoft.daruj.comment.domain.model.Comment
import net.jsoft.daruj.main.domain.model.Donation
import net.jsoft.daruj.main.domain.model.Post

interface PostRepository {
    suspend fun getPosts(vararg ids: String): List<Post>

    suspend fun createPost(post: Post.Mutable): String
    suspend fun updatePost(postId: String, post: Post.Mutable)
    suspend fun deletePost(postId: String)

    suspend fun updatePostPicture(postId: String, pictureUri: Uri)

    suspend fun getRecommendedPosts(reset: Boolean): List<Post>
    suspend fun getSavedPosts(reset: Boolean): List<Post>
    suspend fun getMyPosts(reset: Boolean): List<Post>

    suspend fun searchPosts(criteria: String) : List<Post>

    suspend fun getDonations(reset: Boolean): List<Donation>

    suspend fun postComment(postId: String, comment: Comment.Mutable)
    suspend fun getComment(id: String): Comment
    suspend fun getComments(postId: String): List<Comment>

    companion object {
        const val PostsPerPage = 20
    }
}