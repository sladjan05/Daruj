package net.jsoft.daruj.auth.domain.usecase

import net.jsoft.daruj.auth.domain.Authenticator
import net.jsoft.daruj.auth.exception.WrongCodeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyWithCodeUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(code: String) {
        if (code.isBlank() || code.length < Authenticator.VERIFICATION_CODE_LENGTH) {
            throw WrongCodeException()
        }

        authenticator.verifyWithCode(code)
    }
}