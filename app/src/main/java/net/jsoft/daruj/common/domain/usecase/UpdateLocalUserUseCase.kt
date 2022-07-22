package net.jsoft.daruj.common.domain.usecase

import android.net.Uri
import net.jsoft.daruj.common.domain.UserRepository
import net.jsoft.daruj.common.domain.model.LocalUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateLocalUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        user: LocalUser.Constructable,
        pictureUri: Uri?
    ) {
        userRepository.updateLocalUser(user, pictureUri)
    }
}