package net.jsoft.daruj.auth.data.repository

import kotlinx.coroutines.withContext
import net.jsoft.daruj.auth.data.source.remote.AuthApi
import net.jsoft.daruj.auth.domain.repository.AuthRepository
import net.jsoft.daruj.common.misc.DispatcherProvider

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val dispatcherProvider: DispatcherProvider
) : AuthRepository {
    override suspend fun initialize(vararg args: Pair<Any, Any>) =
        withContext(dispatcherProvider.io) {
            authApi.initialize(*args)
        }

    override suspend fun getAuthState(): AuthRepository.AuthState =
        withContext(dispatcherProvider.io) {
            authApi.getAuthState()
        }

    override suspend fun sendSMSVerification(phoneNumber: String): AuthRepository.VerificationState =
        withContext(dispatcherProvider.io) {
            authApi.sendSMSVerification(phoneNumber)
        }

    override suspend fun verifyWithCode(code: String) =
        withContext(dispatcherProvider.io) {
            authApi.verifyWithCode(code)
        }
}