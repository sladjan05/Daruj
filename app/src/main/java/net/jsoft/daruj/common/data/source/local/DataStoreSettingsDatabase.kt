package net.jsoft.daruj.common.data.source.local

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.first
import net.jsoft.daruj.common.domain.model.LocalSettings
import javax.inject.Inject
import javax.inject.Singleton
import net.jsoft.daruj.common.data.datastore.LocalSettings as DataStoreLocalSettings

@Singleton
class DataStoreSettingsDatabase @Inject constructor(
    application: Application
) : SettingsDatabase {
    private val Context.userPreferencesStore: DataStore<DataStoreLocalSettings> by dataStore(
        fileName = DataStoreFilename,
        serializer = LocalSettingsSerializer
    )

    private val dataStore = application.userPreferencesStore

    override suspend fun getSettings(): LocalSettings {
        val localSettings = dataStore.data.first()

        return LocalSettings(
            hasCompletedIntroduction = localSettings.hasCompletedIntroduction
        )
    }

    override suspend fun updateSettings(settings: LocalSettings) {
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setHasCompletedIntroduction(settings.hasCompletedIntroduction)
                .build()
        }
    }

    companion object {
        private const val DataStoreFilename = "local_settings.pb"
    }
}