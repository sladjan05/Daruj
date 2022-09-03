package net.jsoft.daruj.common.data.source.remote

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseError
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import net.jsoft.daruj.common.domain.repository.AuthRepository
import net.jsoft.daruj.common.exception.InvalidRequestException
import net.jsoft.daruj.common.exception.MissingArgumentException
import net.jsoft.daruj.common.exception.TooManyRequestsException
import net.jsoft.daruj.common.exception.WrongCodeException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseAuthApi @Inject constructor(
    private val auth: FirebaseAuth
) : AuthApi {

    private lateinit var verificationId: String
    private lateinit var activity: Activity

    override suspend fun initialize(vararg args: Pair<Any, Any>) {
        val map = mapOf(*args)
        activity = (map[Activity::class] ?: throw MissingArgumentException()) as Activity
    }

    override suspend fun sendSMSVerification(phoneNumber: String): AuthRepository.VerificationState {
        return coroutineScope {
            suspendCoroutine { continuation ->
                val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        launch {
                            signInWithCredential(credential)
                            continuation.resume(AuthRepository.VerificationState.AUTHENTICATED)
                        }
                    }

                    override fun onVerificationFailed(exception: FirebaseException) {
                        val e = when (exception) {
                            is FirebaseAuthInvalidCredentialsException -> InvalidRequestException()
                            is FirebaseTooManyRequestsException -> TooManyRequestsException()
                            else -> exception
                        }

                        continuation.resumeWithException(e)
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        this@FirebaseAuthApi.verificationId = verificationId
                        continuation.resume(AuthRepository.VerificationState.SENT_SMS)
                    }
                }

                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(AuthRepository.SMS_WAIT_TIME.toLong(), TimeUnit.SECONDS)
                    .setActivity(activity)
                    .setCallbacks(callback)
                    .build()

                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }
    }

    override suspend fun verifyWithCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        try {
            signInWithCredential(credential)
        } catch (e: FirebaseAuthException) {
            throw WrongCodeException()
        }
    }

    override suspend fun getAuthState(): AuthRepository.AuthState {
        return try {
            if (auth.currentUser == null) {
                AuthRepository.AuthState.LOGGED_OUT
            } else {
                auth.currentUser!!.reload().await()
                AuthRepository.AuthState.LOGGED_IN
            }
        } catch (e: FirebaseAuthInvalidUserException) {
            when (e.errorCode) {
                FirebaseError::ERROR_USER_DISABLED.name -> AuthRepository.AuthState.DISABLED
                FirebaseError::ERROR_USER_NOT_FOUND.name -> AuthRepository.AuthState.DELETED
                else -> AuthRepository.AuthState.LOGGED_OUT
            }
        }
    }

    private suspend fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).await()
    }
}