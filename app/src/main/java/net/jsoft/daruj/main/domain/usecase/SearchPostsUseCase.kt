package net.jsoft.daruj.main.domain.usecase

import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(criteria: String): List<Post> {
        return postRepository.searchPosts(criteria)
    }
}