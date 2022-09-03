package net.jsoft.daruj.create_account.domain.usecase

import android.net.Uri
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.usecase.UpdateLocalUserUseCase
import net.jsoft.daruj.common.domain.usecase.UpdateProfilePictureUseCase
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@ViewModelScoped
class CreateUserUseCase @Inject constructor(
    private val updateLocalUser: UpdateLocalUserUseCase,
    private val updateProfilePicture: UpdateProfilePictureUseCase
) {
    suspend operator fun invoke(
        user: LocalUser.Mutable,
        pictureUri: Uri?
    ) = coroutineScope {
        val updateUserJob = launch { updateLocalUser(user) }
        val updateProfilePictureJob = pictureUri?.let { pictureUri ->
            launch { updateProfilePicture(pictureUri) }
        }

        updateUserJob.join()
        updateProfilePictureJob?.join()

        // Waiting for server to complete user creation
        delay(2.seconds)
    }
}