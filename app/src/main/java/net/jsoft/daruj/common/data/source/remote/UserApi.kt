package net.jsoft.daruj.common.data.source.remote

import android.net.Uri
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.User

interface UserApi {
    suspend fun hasCompletedRegistration(): Boolean

    suspend fun getUser(id: String): User
    suspend fun getLocalUser(): LocalUser

    suspend fun updateLocalUser(user: LocalUser.Mutable)
    suspend fun updateProfilePicture(pictureUri: Uri)
}