package net.jsoft.daruj.introduction.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.jsoft.daruj.common.presentation.viewmodel.BasicViewModel
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(

) : BasicViewModel<IntroductionEvent, IntroductionTask>() {

    private val _page = MutableStateFlow(0)
    val page: StateFlow<Int> = _page

    override fun onEvent(event: IntroductionEvent) {
        when (event) {
            is IntroductionEvent.PageSwitch -> {
                _page.value = event.page
            }

            is IntroductionEvent.Next -> {
                if (_page.value == PAGE_COUNT - 1) viewModelScope.launch {
                    _taskFlow.emit(IntroductionTask.Next)
                } else {
                    _page.value += 1
                }
            }
        }
    }

    companion object {
        const val PAGE_COUNT = 3
    }
}