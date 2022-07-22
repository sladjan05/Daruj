package net.jsoft.daruj.auth.domain.usecase

import net.jsoft.daruj.common.domain.Authenticator
import net.jsoft.daruj.common.exception.WrongCodeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyWithCodeUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(code: String) {
        if (code.isBlank() || code.length < 6) {
            throw WrongCodeException()
        }

        authenticator.verifyWithCode(code)
    }
}