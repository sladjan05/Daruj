package net.jsoft.daruj.auth.domain.repository

interface AuthRepository {
    suspend fun initialize(vararg args: Pair<Any, Any>)
    suspend fun getAuthState(): AuthState
    suspend fun sendSMSVerification(phoneNumber: String): VerificationState
    suspend fun verifyWithCode(code: String)

    enum class AuthState {
        LoggedIn,
        LoggedOut,
        Disabled,
        Deleted
    }

    enum class VerificationState {
        SentSms,
        Authenticated
    }

    companion object {
        const val SmsWaitTime = 60
    }
}