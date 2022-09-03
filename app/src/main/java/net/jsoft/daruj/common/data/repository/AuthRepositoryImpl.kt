package net.jsoft.daruj.common.data.repository

import kotlinx.coroutines.withContext
import net.jsoft.daruj.common.data.source.remote.AuthApi
import net.jsoft.daruj.common.domain.repository.AuthRepository
import net.jsoft.daruj.common.misc.DispatcherProvider
import javax.inject.Inject
import javax.inject.Singleton

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