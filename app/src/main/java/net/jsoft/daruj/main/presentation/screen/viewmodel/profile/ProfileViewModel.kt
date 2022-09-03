package net.jsoft.daruj.main.presentation.screen.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.usecase.GetLocalUserUseCase
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.utils.plusAssign
import net.jsoft.daruj.common.utils.uiText
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getLocalUser: GetLocalUserUseCase
) : LoadingViewModel<ProfileEvent, ProfileTask>() {

    var localUser by mutableStateOf<LocalUser?>(null)
        private set

    var page by mutableStateOf(0)
        private set

    init {
        viewModelScope.registerExceptionHandler { e ->
            mTaskFlow += ProfileTask.ShowError(e.uiText)
        }

        viewModelScope.loadSafely {
            localUser = getLocalUser()
        }
    }

    override fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.PageChange -> page = event.page
        }
    }
}