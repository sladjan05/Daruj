package net.jsoft.daruj.auth.domain.usecase

import net.jsoft.daruj.auth.domain.Authenticator
import net.jsoft.daruj.common.exception.InvalidRequestException
import net.jsoft.daruj.common.util.RegexPatterns
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SendSMSVerificationUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(phoneNumber: String) {
        if (!phoneNumber.matches(RegexPatterns.PHONE_NUMBER)) {
            throw InvalidRequestException()
        }

        authenticator.sendSMSVerification(phoneNumber)
    }
}