package net.jsoft.daruj.common.domain.usecase

import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.repository.UserRepository
import net.jsoft.daruj.common.exception.BlankNameException
import net.jsoft.daruj.common.exception.BlankSurameException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateLocalUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: LocalUser.Mutable) {
        if (user.name.isBlank()) throw BlankNameException()
        if (user.surname.isBlank()) throw BlankSurameException()

        userRepository.updateLocalUser(user)
    }
}