package net.jsoft.daruj.common.domain.usecase.user

import net.jsoft.daruj.common.domain.model.LocalSettings
import net.jsoft.daruj.common.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateSettingsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(settings: LocalSettings) {
        userRepository.updateSettings(settings)
    }
}