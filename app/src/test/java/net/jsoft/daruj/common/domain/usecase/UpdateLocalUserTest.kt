package net.jsoft.daruj.common.domain.usecase

import kotlinx.coroutines.runBlocking
import net.jsoft.daruj.common.data.FakeUserRepository
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.Sex
import net.jsoft.daruj.common.exception.BlankNameException
import net.jsoft.daruj.common.exception.BlankSurameException
import org.junit.Test

class UpdateLocalUserTest {

    private val updateLocalUser = UpdateLocalUserUseCase(FakeUserRepository())
    private val user = LocalUser.Constructable(
        name = "",
        surname = "",
        sex = Sex.MALE,
        blood = Blood(
            type = Blood.Type.A,
            rh = Blood.RH.POSITIVE
        ),
        legalId = null,
        isPrivate = false
    )

    @Test(expected = BlankNameException::class)
    fun blankName_throwsException() = runBlocking {
        updateLocalUser(
            user = user.copy(
                surname = "Doe"
            ),
            pictureUri = null
        )
    }

    @Test(expected = BlankSurameException::class)
    fun blankSurname_throwsException() = runBlocking {
        updateLocalUser(
            user = user.copy(
                name = "John"
            ),
            pictureUri = null
        )
    }

    @Test
    fun validUser_succeeds() = runBlocking {
        updateLocalUser(
            user = user.copy(
                name = "John",
                surname = "Doe"
            ),
            pictureUri = null
        )
    }
}