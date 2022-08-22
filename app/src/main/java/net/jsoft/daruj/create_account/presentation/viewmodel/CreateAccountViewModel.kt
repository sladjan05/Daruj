package net.jsoft.daruj.create_account.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.Sex
import net.jsoft.daruj.common.domain.usecase.user.UpdateLocalUserUseCase
import net.jsoft.daruj.common.domain.usecase.user.UpdateProfilePictureUseCase
import net.jsoft.daruj.common.misc.asUiText
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.utils.plusAssign
import net.jsoft.daruj.common.utils.uiText
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val updateLocalUser: UpdateLocalUserUseCase,
    private val updateProfilePicture: UpdateProfilePictureUseCase
) : LoadingViewModel<CreateAccountEvent, CreateAccountTask>() {

    var pictureUri: Uri? by mutableStateOf(null)
        private set

    var name by mutableStateOf("".asUiText())
        private set

    var surname by mutableStateOf("".asUiText())
        private set

    var sex by mutableStateOf(Sex.MALE)
        private set

    var sexExpanded by mutableStateOf(false)
        private set

    var blood by mutableStateOf(
        Blood(
            type = Blood.Type.A,
            rh = Blood.RH.POSITIVE
        )
    )
        private set

    var bloodExpanded by mutableStateOf(false)
        private set

    var legalId by mutableStateOf("".asUiText())
        private set

    var isLegalIdInfoExpanded by mutableStateOf(false)
        private set

    init {
        viewModelScope.registerExceptionHandler { e ->
            mTaskFlow += CreateAccountTask.ShowError(e.uiText)
        }
    }

    override fun onEvent(event: CreateAccountEvent) {
        when (event) {
            is CreateAccountEvent.Dismiss -> setExpanded()

            is CreateAccountEvent.PictureChange -> pictureUri = event.uri

            is CreateAccountEvent.NameChange -> name = event.name.asUiText()
            is CreateAccountEvent.SurnameChange -> surname = event.surname.asUiText()

            is CreateAccountEvent.SexClick -> setExpanded(sex = true)

            is CreateAccountEvent.SexChange -> {
                sex = event.sex
                setExpanded()
            }

            is CreateAccountEvent.BloodClick -> setExpanded(blood = true)

            is CreateAccountEvent.BloodChange -> {
                blood = event.blood
                setExpanded()
            }

            is CreateAccountEvent.LegalIdChange -> legalId = event.legalId.asUiText()

            is CreateAccountEvent.LegalIdInfoClick -> isLegalIdInfoExpanded = !isLegalIdInfoExpanded

            is CreateAccountEvent.CreateAccount -> viewModelScope.loadSafely {
                val user = LocalUser.Mutable(
                    name = name.toString(),
                    surname = surname.toString(),
                    sex = sex,
                    blood = blood,
                    legalId = legalId.toString().ifEmpty { null },
                    isPrivate = true,
                    savedPosts = emptyList()
                )

                val updateUserJob = launch { updateLocalUser(user) }
                val updateProfilePictureJob = pictureUri?.let { pictureUri ->
                    launch { updateProfilePicture(pictureUri) }
                }

                updateUserJob.join()
                updateProfilePictureJob?.join()

                // Waiting for server to complete user creation
                delay(1.seconds)
                mTaskFlow += CreateAccountTask.CreateAccountClick
            }
        }
    }

    private fun setExpanded(
        sex: Boolean = false,
        blood: Boolean = false
    ) {
        sexExpanded = sex
        bloodExpanded = blood
    }
}