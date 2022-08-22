package net.jsoft.daruj.modify_post.domain.usecase

import android.net.Uri
import net.jsoft.daruj.main.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdatePostPictureUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(
        postId: String,
        pictureUri: Uri
    ) {
        postRepository.updatePostPicture(postId, pictureUri)
    }
}