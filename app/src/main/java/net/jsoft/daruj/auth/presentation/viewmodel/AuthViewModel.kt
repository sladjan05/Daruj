package net.jsoft.daruj.auth.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.jsoft.daruj.auth.presentation.screen.Screen
import net.jsoft.daruj.common.domain.usecase.GetSettingsUseCase
import net.jsoft.daruj.common.domain.usecase.UpdateSettingsUseCase
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.utils.plusAssign
import net.jsoft.daruj.common.utils.uiText
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getLocalSettings: GetSettingsUseCase,
    private val updateLocalSettings: UpdateSettingsUseCase
) : LoadingViewModel<Nothing, AuthTask>() {

    var screen: Screen by mutableStateOf(Screen.Phone)
        private set

    init {
        viewModelScope.registerExceptionHandler { e ->
            mTaskFlow += AuthTask.ShowError(e.uiText)
        }

        viewModelScope.launch {
            val settings = getLocalSettings()
            val newSettings = settings.copy(
                hasCompletedIntroduction = true
            )

            updateLocalSettings(newSettings)
        }
    }

    override fun onEvent(event: Nothing) = Unit
}