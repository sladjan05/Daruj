package net.jsoft.daruj.common.domain.usecase

import android.net.Uri
import net.jsoft.daruj.common.domain.UserRepository
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.exception.BlankNameException
import net.jsoft.daruj.common.exception.BlankSurameException
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
        if(user.name.isBlank()) throw BlankNameException()
        if(user.surname.isBlank()) throw BlankSurameException()

        userRepository.updateLocalUser(user, pictureUri)
    }
}