package net.jsoft.daruj.main.data.source.remote

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import net.jsoft.daruj.common.domain.repository.UserRepository
import net.jsoft.daruj.common.misc.Paginator
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.repository.PostRepository

class FirebaseSavedPostsPaginator(
    private val userRepository: UserRepository,
    private val postApi: FirebasePostApi
) : Paginator<Post> {
    private var lastPosts = mutableListOf<String>()

    override suspend fun getNextPage(): List<Post> = coroutineScope {
        val savedPosts = userRepository
            .getLocalUser()
            .mutable
            .savedPosts
            .let { posts ->
                val lastPostId = lastPosts.reversed().find { postId ->
                    posts.contains(postId)
                }

                if (lastPostId == null) {
                    posts
                } else {
                    val last = posts.indexOf(lastPostId)
                    posts.drop(last + 1)
                }
            }


        val deferredPosts = mutableListOf<Deferred<Post>>()

        for (i in savedPosts.indices) {
            if (i >= PostRepository.POSTS_PER_PAGE) break

            val postId = savedPosts[i]

            val defferedPost = async { postApi.getPost(postId) }
            deferredPosts.add(defferedPost)
        }

        val posts = deferredPosts.awaitAll()
        lastPosts.addAll(posts.map { post -> post.data.id })

        posts
    }

    override suspend fun reset() {
        lastPosts = mutableListOf()
    }
}