package net.jsoft.daruj.common.util

import kotlinx.coroutines.CoroutineDispatcher

class DispatcherProvider(
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val unconfined: CoroutineDispatcher
)