package net.jsoft.daruj.auth.data

import net.jsoft.daruj.auth.domain.Authenticator
import net.jsoft.daruj.common.exception.WrongCodeException
import net.jsoft.daruj.common.util.emulateWork
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeAuthenticator @Inject constructor() : Authenticator {
    override val id = ID

    override fun initialize(vararg args: Pair<Any, Any>) = Unit

    override suspend fun sendSMSVerification(
        phoneNumber: String
    ) = emulateWork { Authenticator.State.SENT_SMS }

    override suspend fun verifyWithCode(code: String) = emulateWork {
        if (code != VERIFICATION_CODE) {
            throw WrongCodeException()
        }
    }

    companion object {
        private const val ID = "testId"
        const val VERIFICATION_CODE = "476545"
    }
}