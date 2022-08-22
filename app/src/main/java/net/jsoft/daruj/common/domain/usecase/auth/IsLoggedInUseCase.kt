package net.jsoft.daruj.common.domain.usecase.auth

import net.jsoft.daruj.common.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isLoggedIn()
    }
}