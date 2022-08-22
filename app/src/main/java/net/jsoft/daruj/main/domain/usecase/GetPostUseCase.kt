package net.jsoft.daruj.main.domain.usecase

import net.jsoft.daruj.main.domain.repository.PostRepository
import net.jsoft.daruj.main.domain.model.Post
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(id: String): Post {
        return postRepository.getPost(id)
    }
}