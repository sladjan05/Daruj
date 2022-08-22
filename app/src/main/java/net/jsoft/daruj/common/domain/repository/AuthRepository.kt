package net.jsoft.daruj.common.domain.repository

interface AuthRepository {
    suspend fun initialize(vararg args: Pair<Any, Any>)
    suspend fun isLoggedIn(): Boolean
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