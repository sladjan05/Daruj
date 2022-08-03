package net.jsoft.daruj.auth.domain.usecase

import net.jsoft.daruj.auth.domain.Authenticator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitializeAuthenticatorUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    operator fun invoke(vararg args: Pair<Any, Any>) {
        authenticator.initialize(*args)
    }
}
