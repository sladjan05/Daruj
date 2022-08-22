package net.jsoft.daruj.common.domain.usecase.auth

import net.jsoft.daruj.common.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitializeAuthenticatorUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(vararg args: Pair<Any, Any>) {
        authRepository.initialize(*args)
    }
}
