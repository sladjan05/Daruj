package net.jsoft.daruj.common.misc

import kotlinx.coroutines.CoroutineDispatcher

open class DispatcherProvider(
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val unconfined: CoroutineDispatcher
)