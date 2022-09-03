package net.jsoft.daruj.auth.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.common.domain.repository.AuthRepository
import javax.inject.Inject

@ViewModelScoped
class InitializeAuthenticatorUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(vararg args: Pair<Any, Any>) {
        authRepository.initialize(*args)
    }
}
