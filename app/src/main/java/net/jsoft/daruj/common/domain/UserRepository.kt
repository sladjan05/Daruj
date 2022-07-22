package net.jsoft.daruj.common.domain

import android.net.Uri
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.User

interface UserRepository {
    suspend fun getLocalUser(): LocalUser
    suspend fun updateLocalUser(
        user: LocalUser.Constructable,
        pictureUri: Uri?
    )

    suspend fun getUser(userId: String): User
}