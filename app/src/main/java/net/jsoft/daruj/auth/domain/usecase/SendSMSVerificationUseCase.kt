package net.jsoft.daruj.auth.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.common.domain.repository.AuthRepository
import net.jsoft.daruj.common.exception.InvalidRequestException
import net.jsoft.daruj.common.misc.RegexPatterns
import javax.inject.Inject

@ViewModelScoped
class SendSMSVerificationUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phoneNumber: String): AuthRepository.VerificationState {
        if (!phoneNumber.matches(RegexPatterns.PHONE_NUMBER)) {
            throw InvalidRequestException()
        }

        return authRepository.sendSMSVerification(phoneNumber)
    }
}