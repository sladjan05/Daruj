package net.jsoft.daruj.main.domain.usecase

import net.jsoft.daruj.comment.domain.model.Comment
import net.jsoft.daruj.comment.exception.BlankCommentException
import net.jsoft.daruj.main.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostCommentUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: String, comment: Comment.Mutable) {
        if (comment.content.isBlank()) throw BlankCommentException()

        return postRepository.postComment(postId, comment)
    }
}