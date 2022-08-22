package net.jsoft.daruj.common.data.source.local

import net.jsoft.daruj.common.domain.model.LocalSettings

interface SettingsDatabase {
    suspend fun getSettings(): LocalSettings
    suspend fun updateSettings(settings: LocalSettings)
}