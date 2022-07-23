package net.jsoft.daruj.common.data

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import net.jsoft.daruj.common.data.datastore.LocalSettingsSerializer
import net.jsoft.daruj.common.domain.LocalSettingsRepository
import net.jsoft.daruj.common.domain.model.LocalSettings
import net.jsoft.daruj.common.util.DispatcherProvider
import javax.inject.Inject
import javax.inject.Singleton
import net.jsoft.daruj.common.data.datastore.LocalSettings as DataStoreLocalSettings

@Singleton
class DataStoreLocalSettingsRepository @Inject constructor(
    application: Application,
    private val dispatcherProvider: DispatcherProvider
) : LocalSettingsRepository {

    private val Context.userPreferencesStore: DataStore<DataStoreLocalSettings> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = LocalSettingsSerializer
    )

    private val dataStore = application.userPreferencesStore

    override suspend fun getSettings(): LocalSettings =
        withContext(dispatcherProvider.io) {
            val localSettings = dataStore.data.first()

            LocalSettings(
                hasCompletedIntroduction = localSettings.hasCompletedIntroduction
            )
        }

    override suspend fun updateSettings(settings: LocalSettings): Unit =
        withContext(dispatcherProvider.io) {
            dataStore.updateData { currentSettings ->
                currentSettings.toBuilder()
                    .setHasCompletedIntroduction(settings.hasCompletedIntroduction)
                    .build()
            }
        }

    companion object {
        private const val DATA_STORE_FILE_NAME = "local_settings.pb"
    }
}