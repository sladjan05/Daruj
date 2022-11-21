package net.jsoft.daruj.main.presentation.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.usecase.GetLocalUserUseCase
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.plusAssign
import net.jsoft.daruj.common.util.uiText
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
        viewModelScope.registerExceptionHandler { e -> mTaskFlow += ProfileTask.ShowError(e.uiText) }
        refreshLocalUser()
    }

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.Refresh -> refreshLocalUser()
            is ProfileEvent.PageChange -> page = event.page
        }
    }

    private fun refreshLocalUser() = viewModelScope.loadSafely("REFRESH_LOCAL_USER") {
        localUser = getLocalUser()
    }
}