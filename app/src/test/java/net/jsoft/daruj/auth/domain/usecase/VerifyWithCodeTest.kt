package net.jsoft.daruj.auth.domain.usecase

import kotlinx.coroutines.runBlocking
import net.jsoft.daruj.auth.domain.data.FakeAuthenticator
import net.jsoft.daruj.common.exception.WrongCodeException
import org.junit.Test

class VerifyWithCodeTest {

    private val verifyWithCode = VerifyWithCodeUseCase(FakeAuthenticator())

    @Test(expected = WrongCodeException::class)
    fun emptyCode_throwsException() = runBlocking {
        verifyWithCode("")
    }

    @Test(expected = WrongCodeException::class)
    fun codeLengthDifferentThan6_throwsException() = runBlocking {
        verifyWithCode("54879")
    }

    @Test(expected = WrongCodeException::class)
    fun codeContainingIllegalBCharacters_throwsException() = runBlocking {
        verifyWithCode("12345a")
    }

    @Test
    fun validCode_succeeds() = runBlocking {
        verifyWithCode("123456")
    }
}