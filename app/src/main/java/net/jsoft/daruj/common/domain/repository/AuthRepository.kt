package net.jsoft.daruj.common.domain.repository

interface AuthRepository {
    suspend fun initialize(vararg args: Pair<Any, Any>)
    suspend fun getAuthState(): AuthState
    suspend fun sendSMSVerification(phoneNumber: String): VerificationState
    suspend fun verifyWithCode(code: String)

    enum class AuthState {
        LOGGED_IN,
        LOGGED_OUT,
        DISABLED,
        DELETED
    }

    enum class VerificationState {
        SENT_SMS,
        AUTHENTICATED
    }

    companion object {
        const val SMS_WAIT_TIME = 60
    }
}