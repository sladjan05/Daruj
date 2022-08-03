package net.jsoft.daruj.introduction.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.jsoft.daruj.common.presentation.viewmodel.BaseViewModel
import net.jsoft.daruj.common.util.plusAssign

class IntroductionViewModel : BaseViewModel<IntroductionEvent, IntroductionTask>() {

    var page by mutableStateOf(0)
        private set

    override fun onEvent(event: IntroductionEvent) {
        when (event) {
            is IntroductionEvent.PageSwitch -> page = event.page

            is IntroductionEvent.Next -> viewModelScope.launch {
                if (page == PAGE_COUNT - 1) {
                    mTaskFlow += IntroductionTask.Finish
                } else {
                    page += 1
                    mTaskFlow += IntroductionTask.SwitchPage
                }
            }
        }
    }

    companion object {
        const val PAGE_COUNT = 3
    }
}