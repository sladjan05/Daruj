package net.jsoft.daruj.auth.data

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.jsoft.daruj.auth.domain.Authenticator
import net.jsoft.daruj.auth.exception.InvalidRequestException
import net.jsoft.daruj.auth.exception.RedundantVerificationRequestException
import net.jsoft.daruj.auth.exception.TooManyRequestsException
import net.jsoft.daruj.auth.exception.WrongCodeException
import net.jsoft.daruj.common.exception.UnknownException
import net.jsoft.daruj.common.util.DispatcherProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseAuthenticator @Inject constructor(
    private val auth: FirebaseAuth,
    private val dispatchers: DispatcherProvider
) : Authenticator {

    private lateinit var verificationId: String
    private lateinit var activity: Activity

    override fun initialize(vararg args: Any) {
        args.forEach { arg ->
            when (arg) {
                is Activity -> {
                    activity = arg
                }

                else -> {
                    throw UnknownException()
                }
            }
        }
    }

    override suspend fun sendSMSVerification(phoneNumber: String): Unit =
        withContext(dispatchers.io) {
            suspendCoroutine { continuation ->
                val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        launch(dispatchers.io) {
                            signInWithCredential(credential)
                            continuation.resumeWithException(RedundantVerificationRequestException())
                        }
                    }

                    override fun onVerificationFailed(exception: FirebaseException) {
                        val e = when (exception) {
                            is FirebaseAuthInvalidCredentialsException -> InvalidRequestException()
                            is FirebaseTooManyRequestsException -> TooManyRequestsException()
                            else -> UnknownException()
                        }

                        continuation.resumeWithException(e)
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        this@FirebaseAuthenticator.verificationId = verificationId
                        continuation.resume(Unit)
                    }
                }

                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(activity)
                    .setCallbacks(callback)
                    .build()

                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }

    override suspend fun verifyWithCode(code: String): Unit = withContext(dispatchers.io) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        try {
            signInWithCredential(credential)
        } catch (e: FirebaseAuthException) {
            throw WrongCodeException()
        }
    }

    private suspend fun signInWithCredential(credential: PhoneAuthCredential): Unit =
        withContext(dispatchers.io) {
            auth.signInWithCredential(credential).await()
        }
}