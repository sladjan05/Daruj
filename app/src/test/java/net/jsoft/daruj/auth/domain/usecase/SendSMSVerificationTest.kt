package net.jsoft.daruj.auth.domain.usecase

import kotlinx.coroutines.runBlocking
import net.jsoft.daruj.auth.domain.data.FakeAuthenticator
import net.jsoft.daruj.common.exception.InvalidRequestException
import org.junit.Test

class SendSMSVerificationTest {

    private val sendSMSVerification = SendSMSVerificationUseCase(FakeAuthenticator())

    @Test(expected = InvalidRequestException::class)
    fun emptyPhoneNumber_throwsException() = runBlocking {
        sendSMSVerification("")
    }

    @Test(expected = InvalidRequestException::class)
    fun phoneNumberWithoutLeadingPlus_throwsException() = runBlocking {
        sendSMSVerification("1234512345")
    }

    @Test(expected = InvalidRequestException::class)
    fun phoneNumberContainingLessThan8Numbers_throwsException() = runBlocking {
        sendSMSVerification("+1234567")
    }

    @Test(expected = InvalidRequestException::class)
    fun phoneNumberContainingMoreThan15Numbers_throwsException() = runBlocking {
        sendSMSVerification("+1234567890123456")
    }

    @Test(expected = InvalidRequestException::class)
    fun phoneNumberContainingIllegalCharacters_throwsException() = runBlocking {
        sendSMSVerification("+12345t7345")
    }

    @Test
    fun validPhoneNumber_succeeds()  = runBlocking {
        sendSMSVerification("+1234567890")
    }
}