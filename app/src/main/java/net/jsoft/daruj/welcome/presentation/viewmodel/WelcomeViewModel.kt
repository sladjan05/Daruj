package net.jsoft.daruj.welcome.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.jsoft.daruj.common.presentation.viewmodel.BasicViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(

) : BasicViewModel<WelcomeEvent, WelcomeTask>() {

    override fun onEvent(event: WelcomeEvent) {
        when(event) {
            is WelcomeEvent.Next -> viewModelScope.launch {
                mTaskFlow.emit(WelcomeTask.Next)
            }
        }
    }
}