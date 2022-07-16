package net.jsoft.daruj.auth.domain

interface Authenticator {

    fun initialize(vararg args: Pair<Any, Any>)

    suspend fun sendSMSVerification(phoneNumber: String)
    suspend fun verifyWithCode(code: String)

    companion object {
        const val VERIFICATION_CODE_LENGTH = 6
    }
}