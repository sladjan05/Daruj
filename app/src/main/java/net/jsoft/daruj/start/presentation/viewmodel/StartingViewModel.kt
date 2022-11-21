package net.jsoft.daruj.start.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.auth.domain.repository.AuthRepository
import net.jsoft.daruj.common.domain.usecase.GetSettingsUseCase
import net.jsoft.daruj.common.domain.usecase.HasCompletedRegistrationUseCase
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.plusAssign
import net.jsoft.daruj.auth.domain.usecase.GetAuthStateUseCase
import javax.inject.Inject

@HiltViewModel
class StartingViewModel @Inject constructor(
    private val getSettings: GetSettingsUseCase,
    private val getAuthState: GetAuthStateUseCase,
    private val hasCompletedRegistration: HasCompletedRegistrationUseCase
) : LoadingViewModel<Nothing, StartingTask>() {

    init {
        viewModelScope.loadSafely {
            val authState = getAuthState()

            mTaskFlow += if (authState == AuthRepository.AuthState.LoggedIn) {
                if (hasCompletedRegistration()) {
                    StartingTask.ShowMainScreen
                } else {
                    StartingTask.ShowCreateAccountScreen
                }
            } else {
                // TODO: Other auth states
                val settings = getSettings()

                if (settings.hasCompletedIntroduction) {
                    StartingTask.ShowAuthScreen
                } else {
                    StartingTask.ShowWelcomeScreen
                }
            }
        }
    }

    override fun onEvent(event: Nothing) = Unit
}