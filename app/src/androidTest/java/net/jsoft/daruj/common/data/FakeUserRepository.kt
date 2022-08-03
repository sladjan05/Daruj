package net.jsoft.daruj.common.data

import android.net.Uri
import net.jsoft.daruj.common.domain.UserRepository
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.Sex
import net.jsoft.daruj.common.util.emulateWork
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeUserRepository @Inject constructor() : UserRepository {

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

    override suspend fun getLocalUser() = emulateWork { localUser }

    override suspend fun updateLocalUser(
        user: LocalUser.Constructable,
        pictureUri: Uri?
    ) = emulateWork { }

    override suspend fun getUser(userId: String) = emulateWork {
        if (userId == localUser.id) {
            localUser
        } else {
            throw NotImplementedError()
        }
    }
}