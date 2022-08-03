package net.jsoft.daruj.auth.domain

interface Authenticator {
    val id: String

    fun initialize(vararg args: Pair<Any, Any>)
    suspend fun sendSMSVerification(phoneNumber: String): State
    suspend fun verifyWithCode(code: String)

    enum class State {
        SENT_SMS,
        AUTHENTICATED
    }

    companion object {
        const val SMS_WAIT_TIME = 60
    }
}