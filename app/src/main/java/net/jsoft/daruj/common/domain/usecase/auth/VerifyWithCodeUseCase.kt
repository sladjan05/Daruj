package net.jsoft.daruj.common.domain.usecase.auth

import net.jsoft.daruj.common.domain.repository.AuthRepository
import net.jsoft.daruj.common.exception.WrongCodeException
import net.jsoft.daruj.common.misc.RegexPatterns
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyWithCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(code: String) {
        if (!code.matches(RegexPatterns.VERIFICATION_CODE)) {
            throw WrongCodeException()
        }

        authRepository.verifyWithCode(code)
    }
}