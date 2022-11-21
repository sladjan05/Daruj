package net.jsoft.daruj.common.domain.usecase

import net.jsoft.daruj.common.domain.model.LocalSettings
import net.jsoft.daruj.common.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSettingsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): LocalSettings {
        return userRepository.getSettings()
    }
}