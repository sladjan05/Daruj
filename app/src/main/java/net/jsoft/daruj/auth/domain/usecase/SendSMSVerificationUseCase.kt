package net.jsoft.daruj.auth.domain.usecase

import android.util.Patterns
import net.jsoft.daruj.auth.domain.Authenticator
import net.jsoft.daruj.auth.exception.InvalidRequestException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SendSMSVerificationUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(phoneNumber: String) {
        if(!Patterns.PHONE.matcher(phoneNumber).matches()) {
            throw InvalidRequestException()
        }

        authenticator.sendSMSVerification(phoneNumber)
    }
}