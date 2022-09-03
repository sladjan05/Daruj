package net.jsoft.daruj.start.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.domain.repository.AuthRepository
import net.jsoft.daruj.common.domain.usecase.GetSettingsUseCase
import net.jsoft.daruj.common.domain.usecase.HasCompletedRegistrationUseCase
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.utils.plusAssign
import net.jsoft.daruj.start.domain.usecase.GetAuthStateUseCase
import javax.inject.Inject

@HiltViewModel
class StartingViewModel @Inject constructor(
    private val getSettings: GetSettingsUseCase,
    private val getAuthState: GetAuthStateUseCase,
    private val hasCompletedRegistration: HasCompletedRegistrationUseCase
) : LoadingViewModel<Nothing, StartingTask>() {

    init {
        viewModelScope.loadSafely {
            Log.d("Screen", "AAAAAAAAAA")
            val authState = getAuthState()
            Log.d("Screen", "AAAAAAAAAA")

            mTaskFlow += if (authState == AuthRepository.AuthState.LOGGED_IN) {
                Log.d("Screen", "This")

                if (hasCompletedRegistration()) {
                    StartingTask.ShowMainScreen
                } else {
                    StartingTask.ShowCreateAccountScreen
                }
            } else {
                // TODO: Other auth states
                val settings = getSettings()
                Log.d("Screen", "Other")

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