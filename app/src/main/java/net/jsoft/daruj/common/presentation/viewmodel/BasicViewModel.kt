package net.jsoft.daruj.common.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BasicViewModel<Event, Task> : ViewModel() {
    protected val _taskFlow = MutableSharedFlow<Task>()
    val taskFlow: SharedFlow<Task> = _taskFlow

    abstract fun onEvent(event: Event)
}