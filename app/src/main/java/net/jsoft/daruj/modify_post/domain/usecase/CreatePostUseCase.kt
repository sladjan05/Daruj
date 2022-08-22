package net.jsoft.daruj.modify_post.domain.usecase

import net.jsoft.daruj.common.exception.BlankNameException
import net.jsoft.daruj.common.exception.BlankSurameException
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.repository.PostRepository
import net.jsoft.daruj.modify_post.exception.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreatePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(post: Post.Mutable): String {
        if (post.name.isBlank()) throw BlankNameException()
        if (post.surname.isBlank()) throw BlankSurameException()
        if (post.parentName.isBlank()) throw BlankParentNameException()
        if (post.location.isBlank()) throw BlankLocationException()
        if (post.donorsRequired == 0) throw BlankDonorsRequiredException()
        if (post.description.isBlank()) throw BlankDescriptionException()

        return postRepository.createPost(post)
    }
}