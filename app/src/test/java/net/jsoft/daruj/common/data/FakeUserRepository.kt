package net.jsoft.daruj.common.data

import android.net.Uri
import net.jsoft.daruj.common.domain.UserRepository
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.User

class FakeUserRepository : UserRepository {
    override suspend fun getLocalUser(): LocalUser = getLocalUser()
    override suspend fun updateLocalUser(user: LocalUser.Constructable, pictureUri: Uri?) = Unit
    override suspend fun getUser(userId: String): User = getUser(userId)
}