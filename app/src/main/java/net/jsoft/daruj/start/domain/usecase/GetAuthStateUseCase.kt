package net.jsoft.daruj.start.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.common.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class GetAuthStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AuthRepository.AuthState {
        return authRepository.getAuthState()
    }
}