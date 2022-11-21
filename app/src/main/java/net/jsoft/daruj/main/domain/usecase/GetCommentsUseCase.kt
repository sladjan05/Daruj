package net.jsoft.daruj.main.domain.usecase

import net.jsoft.daruj.comment.domain.model.Comment
import net.jsoft.daruj.main.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCommentsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: String): List<Comment> {
        return postRepository.getComments(postId)
    }
}