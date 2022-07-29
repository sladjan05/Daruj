package net.jsoft.daruj.auth.domain.usecase

import net.jsoft.daruj.auth.domain.Authenticator
import net.jsoft.daruj.common.exception.WrongCodeException
import net.jsoft.daruj.common.util.RegexPatterns
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyWithCodeUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(code: String) {
        if (!code.matches(RegexPatterns.VERIFICATION_CODE)) {
            throw WrongCodeException()
        }

        authenticator.verifyWithCode(code)
    }
}