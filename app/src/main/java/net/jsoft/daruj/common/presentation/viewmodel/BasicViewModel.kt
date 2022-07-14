package net.jsoft.daruj.common.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BasicViewModel<Event, Task> : ViewModel() {
    protected val mTaskFlow = MutableSharedFlow<Task>()
    val taskFlow: SharedFlow<Task> = mTaskFlow

    abstract fun onEvent(event: Event)
}