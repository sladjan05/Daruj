package net.jsoft.daruj.common.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.common.domain.repository.UserRepository
import net.jsoft.daruj.common.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: String): User {
        return userRepository.getUser(id)
    }
}