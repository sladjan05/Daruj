package net.jsoft.daruj.common.data

import net.jsoft.daruj.common.domain.LocalSettingsRepository
import net.jsoft.daruj.common.domain.model.LocalSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeLocalSettingsRepository @Inject constructor() : LocalSettingsRepository {
    private var settings = LocalSettings(
        hasCompletedIntroduction = false
    )

    override suspend fun getSettings(): LocalSettings = settings

    override suspend fun updateSettings(settings: LocalSettings) {
        this.settings = settings
    }
}