package net.jsoft.daruj.auth.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.auth.domain.repository.AuthRepository
import net.jsoft.daruj.common.exception.WrongCodeException
import net.jsoft.daruj.common.misc.RegexPatterns
import javax.inject.Inject

@ViewModelScoped
class VerifyWithCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(code: String) {
        if (!code.matches(RegexPatterns.VerificationCode)) throw WrongCodeException()

        authRepository.verifyWithCode(code)
    }
}