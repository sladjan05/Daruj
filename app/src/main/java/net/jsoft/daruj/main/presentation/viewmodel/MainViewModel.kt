package net.jsoft.daruj.main.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import net.jsoft.daruj.common.presentation.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel<MainEvent, MainTask>() {

    var page by mutableStateOf(0)
        private set

    override fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.PageChange -> page = event.page
        }
    }
}