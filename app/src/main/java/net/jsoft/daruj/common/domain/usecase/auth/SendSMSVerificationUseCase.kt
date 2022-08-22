package net.jsoft.daruj.common.domain.usecase.auth

import net.jsoft.daruj.common.domain.repository.AuthRepository
import net.jsoft.daruj.common.exception.InvalidRequestException
import net.jsoft.daruj.common.misc.RegexPatterns
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SendSMSVerificationUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phoneNumber: String): AuthRepository.State {
        if (!phoneNumber.matches(RegexPatterns.PHONE_NUMBER)) {
            throw InvalidRequestException()
        }

        return authRepository.sendSMSVerification(phoneNumber)
    }
}