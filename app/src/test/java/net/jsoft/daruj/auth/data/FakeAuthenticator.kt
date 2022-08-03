package net.jsoft.daruj.auth.data

import net.jsoft.daruj.auth.domain.Authenticator

class FakeAuthenticator : Authenticator {
    override val id = ID

    override fun initialize(vararg args: Pair<Any, Any>) = Unit
    override suspend fun sendSMSVerification(phoneNumber: String) = Authenticator.State.SENT_SMS
    override suspend fun verifyWithCode(code: String) = Unit

    companion object {
        private const val ID = "testId"
    }
}