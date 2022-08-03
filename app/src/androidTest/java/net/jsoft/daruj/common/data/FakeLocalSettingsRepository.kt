package net.jsoft.daruj.common.data

import net.jsoft.daruj.common.domain.LocalSettingsRepository
import net.jsoft.daruj.common.domain.model.LocalSettings
import net.jsoft.daruj.common.util.emulateWork
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeLocalSettingsRepository @Inject constructor() : LocalSettingsRepository {
    private var settings = LocalSettings(
        hasCompletedIntroduction = true
    )

    override suspend fun getSettings(): LocalSettings = emulateWork { settings }

    override suspend fun updateSettings(settings: LocalSettings) = emulateWork {
        this@FakeLocalSettingsRepository.settings = settings
    }
}