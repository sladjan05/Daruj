package net.jsoft.daruj.auth.data.source.remote

import net.jsoft.daruj.auth.domain.repository.AuthRepository

interface AuthApi {
    suspend fun initialize(vararg args: Pair<Any, Any>)
    suspend fun getAuthState(): AuthRepository.AuthState
    suspend fun sendSMSVerification(phoneNumber: String): AuthRepository.VerificationState
    suspend fun verifyWithCode(code: String)
}