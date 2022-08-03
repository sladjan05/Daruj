package net.jsoft.daruj.common.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.jsoft.daruj.common.util.DispatcherProvider
import net.jsoft.daruj.common.util.plusAssign
import javax.inject.Inject

abstract class LoadingViewModel<Event, Task> : BaseViewModel<Event, Task>() {

    @Inject
    protected lateinit var dispatcherProvider: DispatcherProvider

    var isLoading by mutableStateOf(false)
        private set

    private val exceptions = MutableSharedFlow<Exception>()

    protected suspend fun <T> loadSafely(block: suspend () -> T): T? {
        return try {
            isLoading = true
            withContext(dispatcherProvider.io) { block() }
        } catch (e: Exception) {
            exceptions += e
            null
        } finally {
            isLoading = false
        }
    }

    protected suspend infix fun <T> T.ifSuccessful(block: suspend (result: T & Any) -> Unit) {
        if (this != null) {
            block(this)
        }
    }

    protected fun CoroutineScope.registerExceptionHandler(
        handler: suspend (e: Exception) -> Unit
    ) = launch { exceptions.collectLatest(handler) }
}