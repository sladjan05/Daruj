package net.jsoft.daruj.common.domain.repository

import android.net.Uri
import net.jsoft.daruj.common.domain.model.LocalSettings
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.User

interface UserRepository {
    suspend fun hasCompletedRegistration(): Boolean

    suspend fun getUser(id: String): User
    suspend fun getLocalUser(): LocalUser
    suspend fun getSettings(): LocalSettings

    suspend fun updateLocalUser(user: LocalUser.Mutable)
    suspend fun updateProfilePicture(pictureUri: Uri)
    suspend fun updateSettings(settings: LocalSettings)
    suspend fun setPostSaved(postId: String, saved: Boolean)
}