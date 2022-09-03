package net.jsoft.daruj.main.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.asUiText
import net.jsoft.daruj.common.presentation.viewmodel.BaseViewModel
import net.jsoft.daruj.common.utils.plusAssign

class MainViewModel : BaseViewModel<MainEvent, MainTask>() {
    var page by mutableStateOf(0)
        private set

    override fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.PageChange -> viewModelScope.launch {
                when (event.page) {
                    1 -> mTaskFlow += MainTask.ShowError(R.string.tx_soon.asUiText())
                    else -> page = event.page
                }
            }
        }
    }
}