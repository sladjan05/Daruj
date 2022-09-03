package net.jsoft.daruj.common.domain.usecase

import android.net.Uri
import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.common.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class UpdateProfilePictureUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(pictureUri: Uri) {
        userRepository.updateProfilePicture(pictureUri)
    }
}