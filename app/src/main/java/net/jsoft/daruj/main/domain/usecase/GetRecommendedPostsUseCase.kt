package net.jsoft.daruj.main.domain.usecase

import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRecommendedPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(reset: Boolean = false): List<Post> {
        return postRepository.getRecommendedPosts(reset)
    }
}