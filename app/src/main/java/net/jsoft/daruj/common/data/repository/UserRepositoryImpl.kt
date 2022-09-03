package net.jsoft.daruj.common.data.repository

import android.app.Application
import android.net.Uri
import androidx.work.*
import kotlinx.coroutines.withContext
import net.jsoft.daruj.common.data.source.local.SettingsDatabase
import net.jsoft.daruj.common.data.source.remote.UserApi
import net.jsoft.daruj.common.domain.model.LocalSettings
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.User
import net.jsoft.daruj.common.domain.repository.UserRepository
import net.jsoft.daruj.common.misc.DispatcherProvider
import net.jsoft.daruj.common.misc.JsonParser
import javax.inject.Inject
import javax.inject.Singleton

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val settingsDatabase: SettingsDatabase,

    private val application: Application,
    private val jsonParser: JsonParser,
    private val dispatcherProvider: DispatcherProvider
) : UserRepository {

    override suspend fun hasCompletedRegistration(): Boolean =
        withContext(dispatcherProvider.io) {
            userApi.hasCompletedRegistration()
        }

    override suspend fun getUser(id: String): User =
        withContext(dispatcherProvider.io) {
            userApi.getUser(id)
        }

    override suspend fun getLocalUser(): LocalUser =
        withContext(dispatcherProvider.io) {
            userApi.getLocalUser()
        }

    override suspend fun getSettings(): LocalSettings =
        withContext(dispatcherProvider.io) {
            settingsDatabase.getSettings()
        }

    override suspend fun updateLocalUser(user: LocalUser.Mutable): Unit =
        withContext(dispatcherProvider.io) {
            val userJson = jsonParser.toJson(user)
            val data = workDataOf(UpdateLocalUserWorker.LOCAL_USER_JSON to userJson)
            val request = OneTimeWorkRequest.Builder(UpdateLocalUserWorker::class.java)
                .setInputData(data)
                .build()

            WorkManager.getInstance(application)
                .enqueueUniqueWork(
                    "UPDATE_LOCAL_USER",
                    ExistingWorkPolicy.REPLACE,
                    request
                )
        }

    override suspend fun updateProfilePicture(pictureUri: Uri) =
        withContext(dispatcherProvider.io) {
            userApi.updateProfilePicture(pictureUri)
        }

    override suspend fun updateSettings(settings: LocalSettings) =
        withContext(dispatcherProvider.io) {
            settingsDatabase.updateSettings(settings)
        }

    override suspend fun setPostSaved(postId: String, saved: Boolean) =
        withContext(dispatcherProvider.io) {
            userApi.setPostSaved(postId, saved)
        }
}