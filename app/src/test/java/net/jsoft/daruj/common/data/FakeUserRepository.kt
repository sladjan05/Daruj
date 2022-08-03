package net.jsoft.daruj.common.data

import android.net.Uri
import net.jsoft.daruj.common.domain.UserRepository
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.Sex
import net.jsoft.daruj.common.domain.model.User

class FakeUserRepository : UserRepository {
    private val localUser = LocalUser(
        id = "testUserId",
        pictureUri = null,
        name = "John",
        surname = "Doe",
        displayName = "John Doe",
        sex = Sex.MALE,
        blood = Blood(
            type = Blood.Type.A,
            rh = Blood.RH.POSITIVE
        ),
        legalId = null,
        isPrivate = false
    )

    override suspend fun getLocalUser(): LocalUser = getLocalUser()
    override suspend fun updateLocalUser(user: LocalUser.Constructable, pictureUri: Uri?) = Unit
    override suspend fun getUser(userId: String): User = getUser(userId)
}