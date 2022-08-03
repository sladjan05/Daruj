package net.jsoft.daruj.auth.data

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.jsoft.daruj.auth.domain.Authenticator
import net.jsoft.daruj.common.exception.InvalidRequestException
import net.jsoft.daruj.common.exception.MissingArgumentException
import net.jsoft.daruj.common.exception.TooManyRequestsException
import net.jsoft.daruj.common.exception.WrongCodeException
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

    override lateinit var id: String
        private set

    private lateinit var verificationId: String
    private lateinit var activity: Activity

    override fun initialize(vararg args: Pair<Any, Any>) {
        val map = mapOf(*args)
        activity = (map[Activity::class] ?: throw MissingArgumentException()) as Activity
    }

    override suspend fun sendSMSVerification(phoneNumber: String): Authenticator.State =
        withContext(dispatchers.io) {
            suspendCoroutine { continuation ->
                val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        launch(dispatchers.io) {
                            signInWithCredential(credential)
                            continuation.resume(Authenticator.State.AUTHENTICATED)
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
                        this@FirebaseAuthenticator.verificationId = verificationId
                        continuation.resume(Authenticator.State.SENT_SMS)
                    }
                }

                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(Authenticator.SMS_WAIT_TIME.toLong(), TimeUnit.SECONDS)
                    .setActivity(activity)
                    .setCallbacks(callback)
                    .build()

                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }

    override suspend fun verifyWithCode(code: String) = withContext(dispatchers.io) {
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
            id = auth.currentUser!!.uid
        }
}