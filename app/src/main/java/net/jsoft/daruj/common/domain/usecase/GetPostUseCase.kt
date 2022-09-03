package net.jsoft.daruj.common.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.main.domain.repository.PostRepository
import net.jsoft.daruj.main.domain.model.Post
import javax.inject.Inject

@ViewModelScoped
class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(id: String): Post {
        return postRepository.getPosts(id).first()
    }
}