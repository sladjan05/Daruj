package net.jsoft.daruj.common.domain.usecase

import net.jsoft.daruj.common.domain.LocalSettingsRepository
import net.jsoft.daruj.common.domain.model.LocalSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateLocalSettingsUseCase @Inject constructor(
    private val localSettingsRepository: LocalSettingsRepository
) {
    suspend operator fun invoke(settings: LocalSettings) {
        localSettingsRepository.updateSettings(settings)
    }
}