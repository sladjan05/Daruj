package net.jsoft.daruj.start.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.domain.usecase.auth.IsLoggedInUseCase
import net.jsoft.daruj.common.domain.usecase.user.GetSettingsUseCase
import net.jsoft.daruj.common.domain.usecase.user.HasCompletedRegistrationUseCase
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.utils.plusAssign
import javax.inject.Inject

@HiltViewModel
class StartingViewModel @Inject constructor(
    private val getSettings: GetSettingsUseCase,
    private val isLoggedIn: IsLoggedInUseCase,
    private val hasCompletedRegistration: HasCompletedRegistrationUseCase
) : LoadingViewModel<Nothing, StartingTask>() {

    init {
        viewModelScope.loadSafely {
            mTaskFlow += if (isLoggedIn()) {
                if (hasCompletedRegistration()) {
                    StartingTask.ShowMainScreen
                } else {
                    StartingTask.ShowCreateAccountScreen
                }
            } else {
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