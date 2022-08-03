package net.jsoft.daruj.start.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.jsoft.daruj.common.domain.usecase.GetLocalSettingsUseCase
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.plusAssign
import javax.inject.Inject

@HiltViewModel
class StartingViewModel @Inject constructor(
    private val getLocalSettings: GetLocalSettingsUseCase
) : LoadingViewModel<Nothing, StartingTask>() {

    init {
        viewModelScope.launch {
            loadSafely {
                getLocalSettings()
            } ifSuccessful { settings ->
                mTaskFlow += if (settings.hasCompletedIntroduction) {
                    StartingTask.ShowAuthScreen
                } else {
                    StartingTask.ShowWelcomeScreen
                }
            }
        }
    }

    override fun onEvent(event: Nothing) = Unit
}