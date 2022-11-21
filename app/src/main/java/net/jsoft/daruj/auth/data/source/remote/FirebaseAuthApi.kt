package net.jsoft.daruj.auth.data.source.remote

import android.app.Activity
import com.google.firebase.FirebaseError
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import net.jsoft.daruj.auth.domain.repository.AuthRepository
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
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val messaging: FirebaseMessaging
) : AuthApi {

    private lateinit var verificationId: String
    private lateinit var activity: Activity

    private val localUserDataReference: DocumentReference
        get() = firestore.collection(UserData).document(auth.currentUser!!.uid)

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
                            continuation.resume(AuthRepository.VerificationState.Authenticated)
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
                        continuation.resume(AuthRepository.VerificationState.SentSms)
                    }
                }

                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(AuthRepository.SmsWaitTime.toLong(), TimeUnit.SECONDS)
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
                AuthRepository.AuthState.LoggedOut
            } else {
                auth.currentUser!!.reload().await()
                AuthRepository.AuthState.LoggedIn
            }
        } catch (e: FirebaseAuthInvalidUserException) {
            when (e.errorCode) {
                FirebaseError::ERROR_USER_DISABLED.name -> AuthRepository.AuthState.Disabled
                FirebaseError::ERROR_USER_NOT_FOUND.name -> AuthRepository.AuthState.Deleted
                else -> AuthRepository.AuthState.LoggedOut
            }
        }
    }

    private suspend fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).await()

        val token = messaging.token.await()
        val data = object { val tokens = FieldValue.arrayUnion(token) }

        localUserDataReference.set(data, SetOptions.merge()).await()
    }

    companion object {
        private const val UserData = "userData"
    }
}