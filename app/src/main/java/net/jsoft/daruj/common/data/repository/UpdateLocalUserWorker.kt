package net.jsoft.daruj.common.data.repository

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext
import net.jsoft.daruj.common.data.source.remote.FirebaseUserApi
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.misc.DispatcherProvider
import net.jsoft.daruj.common.misc.JsonParser
import net.jsoft.daruj.common.misc.fromJson

@HiltWorker
class UpdateLocalUserWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,

    private val userApi: FirebaseUserApi,

    private val jsonParser: JsonParser,
    private val dispatcherProvider: DispatcherProvider
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(dispatcherProvider.io) {
        try {
            val localUserJson = inputData.getString(LOCAL_USER_JSON)!!
            val localUser = jsonParser.fromJson<LocalUser.Mutable>(localUserJson)

            userApi.updateLocalUser(localUser)

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val LOCAL_USER_JSON = "LOCAL_USER"
    }
}