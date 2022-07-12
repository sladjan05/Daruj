package net.jsoft.daruj.auth.domain.usecase

import net.jsoft.daruj.auth.domain.Authenticator
import net.jsoft.daruj.auth.exception.WrongCodeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitializeAuthenticatorUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    operator fun invoke(vararg args: Any) {
        authenticator.initialize(*args)
    }
}