package net.jsoft.daruj.main.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(id: String): Post {
        return postRepository.getPosts(id).first()
    }
}