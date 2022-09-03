package net.jsoft.daruj.main.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.common.domain.repository.UserRepository
import javax.inject.Inject

@ViewModelScoped
class SetPostSavedUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(postId: String, saved: Boolean) {
        userRepository.setPostSaved(postId, saved)
    }
}