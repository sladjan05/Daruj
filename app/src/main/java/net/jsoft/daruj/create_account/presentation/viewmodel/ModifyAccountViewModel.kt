package net.jsoft.daruj.create_account.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.Sex
import net.jsoft.daruj.common.domain.usecase.GetLocalUserUseCase
import net.jsoft.daruj.common.domain.usecase.UpdateLocalUserUseCase
import net.jsoft.daruj.common.domain.usecase.UpdateProfilePictureUseCase
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.misc.asUiText
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.ifTrue
import net.jsoft.daruj.common.util.plusAssign
import net.jsoft.daruj.common.util.uiText
import net.jsoft.daruj.create_account.presentation.ModifyAccountActivity
import javax.inject.Inject

@HiltViewModel
class ModifyAccountViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

    private val getLocalUser: GetLocalUserUseCase,

    private val updateLocalUser: UpdateLocalUserUseCase,
    private val updateProfilePicture: UpdateProfilePictureUseCase
) : LoadingViewModel<CreateAccountEvent, CreateAccountTask>() {

    var pictureUri: Uri? by mutableStateOf(null)
        private set

    var name by mutableStateOf(UiText.Empty)
        private set

    var surname by mutableStateOf(UiText.Empty)
        private set

    var sex by mutableStateOf(Sex.Male)
        private set

    var sexExpanded by mutableStateOf(false)
        private set

    var blood by mutableStateOf(Blood.fromString("A+"))
        private set

    var bloodExpanded by mutableStateOf(false)
        private set

    var isPrivate by mutableStateOf(true)
        private set

    private val intention = savedStateHandle.get<ModifyAccountActivity.Intention>(ModifyAccountActivity.Intention)!!
    private var pictureChanged = false

    init {
        viewModelScope.registerExceptionHandler { e -> mTaskFlow += CreateAccountTask.ShowError(e.uiText) }

        if (intention == ModifyAccountActivity.Intention.EditAccount) viewModelScope.loadSafely {
            val localUser = getLocalUser()
            val mutable = localUser.mutable
            val data = localUser.data

            pictureUri = data.pictureUri

            name = mutable.name.asUiText()
            surname = mutable.surname.asUiText()

            sex = mutable.sex
            blood = mutable.blood

            isPrivate = mutable.isPrivate
        }
    }

    override fun onEvent(event: CreateAccountEvent) {
        when (event) {
            is CreateAccountEvent.Dismiss -> setExpanded()

            is CreateAccountEvent.PictureChange -> {
                pictureUri = event.uri
                pictureChanged = true
            }

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

            is CreateAccountEvent.IsPrivateClick -> isPrivate = !isPrivate

            is CreateAccountEvent.Finish -> viewModelScope.loadSafely {
                val user = LocalUser.Mutable(
                    name = name.toString(),
                    surname = surname.toString(),
                    sex = sex,
                    blood = blood,
                    isPrivate = isPrivate
                )

                val createUserJob = launch { updateLocalUser(user) }
                val updateProfilePictureJob = pictureUri?.let { pictureUri ->
                    pictureChanged.ifTrue { launch { updateProfilePicture(pictureUri) } }
                }

                createUserJob.join()
                updateProfilePictureJob?.join()

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