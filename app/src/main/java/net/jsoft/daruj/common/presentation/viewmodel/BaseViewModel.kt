package net.jsoft.daruj.common.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel<Event, Task> : ViewModel() {
    protected val mTaskFlow = MutableSharedFlow<Task>()
    val taskFlow = mTaskFlow.asSharedFlow()

    abstract fun onEvent(event: Event)
}