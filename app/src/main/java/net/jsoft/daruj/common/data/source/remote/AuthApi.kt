package net.jsoft.daruj.common.data.source.remote

import net.jsoft.daruj.common.domain.repository.AuthRepository

interface AuthApi {
    suspend fun initialize(vararg args: Pair<Any, Any>)
    suspend fun isLoggedIn(): Boolean
    suspend fun sendSMSVerification(phoneNumber: String): AuthRepository.State
    suspend fun verifyWithCode(code: String)
}