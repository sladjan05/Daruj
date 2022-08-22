package net.jsoft.daruj.common.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.common.utils.plusAssign
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class LoadingViewModel<Event, Task> : BaseViewModel<Event, Task>() {
    var isLoading by mutableStateOf(false)
        private set

    private val exceptions = MutableSharedFlow<Exception>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable is Exception) viewModelScope.launch {
            exceptions += throwable
        }

        isLoading = false
    }

    private val jobs = mutableMapOf<String, Job>()

    protected fun CoroutineScope.launch(
        id: String,
        context: CoroutineContext = EmptyCoroutineContext,
        coroutineStart: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        jobs[id]?.cancel()
        val job = launch(context, coroutineStart, block)
        jobs[id] = job

        return job
    }

    protected fun CoroutineScope.loadSafely(
        context: CoroutineContext = EmptyCoroutineContext,
        coroutineStart: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = launch(context + exceptionHandler, coroutineStart) {
        isLoading = true
        block()
        isLoading = false
    }

    protected fun CoroutineScope.loadSafely(
        id: String,
        context: CoroutineContext = EmptyCoroutineContext,
        coroutineStart: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = launch(id, context + exceptionHandler, coroutineStart) {
        isLoading = true
        block()
        isLoading = false
    }

    protected fun CoroutineScope.registerExceptionHandler(
        handler: suspend (e: Exception) -> Unit
    ): Job = launch { exceptions.collectLatest(handler) }
}