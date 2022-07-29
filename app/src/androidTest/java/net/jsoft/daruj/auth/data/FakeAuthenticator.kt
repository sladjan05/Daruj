package net.jsoft.daruj.auth.data

import net.jsoft.daruj.auth.domain.Authenticator
import net.jsoft.daruj.common.exception.WrongCodeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeAuthenticator @Inject constructor() : Authenticator {
    override val id = ID

    override fun initialize(vararg args: Pair<Any, Any>) = Unit
    override suspend fun sendSMSVerification(phoneNumber: String) = Unit

    override suspend fun verifyWithCode(code: String) {
        if (code != VERIFICATION_CODE) {
            throw WrongCodeException()
        }
    }

    companion object {
        private const val ID = "testId"
        const val VERIFICATION_CODE = "123456"
    }
}