package net.jsoft.daruj.common.domain.usecase.user

import net.jsoft.daruj.common.domain.repository.UserRepository
import net.jsoft.daruj.common.domain.model.LocalUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLocalUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): LocalUser {
        return userRepository.getLocalUser()
    }
}