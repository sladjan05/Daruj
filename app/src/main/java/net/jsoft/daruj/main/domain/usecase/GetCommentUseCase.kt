package net.jsoft.daruj.main.domain.usecase

import net.jsoft.daruj.comment.domain.model.Comment
import net.jsoft.daruj.main.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCommentUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(id: String): Comment {
        return postRepository.getComment(id)
    }
}