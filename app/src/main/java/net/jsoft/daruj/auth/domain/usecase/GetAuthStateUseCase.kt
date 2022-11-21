package net.jsoft.daruj.auth.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.auth.domain.repository.AuthRepository
import javax.inject.Inject

@ViewModelScoped
class GetAuthStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AuthRepository.AuthState {
        return authRepository.getAuthState()
    }
}