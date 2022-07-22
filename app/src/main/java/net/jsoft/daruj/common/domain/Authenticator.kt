package net.jsoft.daruj.common.domain

interface Authenticator {
    val id: String

    fun initialize(vararg args: Pair<Any, Any>)

    suspend fun sendSMSVerification(phoneNumber: String)
    suspend fun verifyWithCode(code: String)

    companion object {
        const val SMS_WAIT_TIME = 60
    }
}