package net.jsoft.daruj.auth.domain.data

import net.jsoft.daruj.auth.domain.Authenticator

class FakeAuthenticator : Authenticator {
    override val id = "test_id_123456"

    override fun initialize(vararg args: Pair<Any, Any>) = Unit
    override suspend fun sendSMSVerification(phoneNumber: String) = Unit
    override suspend fun verifyWithCode(code: String)  = Unit
}