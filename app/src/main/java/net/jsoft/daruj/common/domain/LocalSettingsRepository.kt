package net.jsoft.daruj.common.domain

import net.jsoft.daruj.common.domain.model.LocalSettings

interface LocalSettingsRepository {
    suspend fun getSettings(): LocalSettings
    suspend fun updateSettings(settings: LocalSettings)
}