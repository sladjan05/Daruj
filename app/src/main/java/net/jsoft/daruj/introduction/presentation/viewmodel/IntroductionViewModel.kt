package net.jsoft.daruj.introduction.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import net.jsoft.daruj.common.presentation.viewmodel.BaseViewModel

class IntroductionViewModel : BaseViewModel<IntroductionEvent, Nothing>() {

    var page by mutableStateOf(0)
        private set

    override fun onEvent(event: IntroductionEvent) {
        when (event) {
            is IntroductionEvent.PageChange -> page = event.page
        }
    }

    companion object {
        const val PAGE_COUNT = 3
    }
}