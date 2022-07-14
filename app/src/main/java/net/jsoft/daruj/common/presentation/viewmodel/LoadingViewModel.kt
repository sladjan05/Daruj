package net.jsoft.daruj.common.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class LoadingViewModel<Event, Task> : BasicViewModel<Event, Task>() {
    var isLoading by mutableStateOf(false)
        protected set

    protected inline fun load(block: () -> Unit) {
        isLoading = true
        block()
        isLoading = false
    }
}