package net.jsoft.daruj.common.util

import kotlinx.coroutines.flow.MutableSharedFlow

suspend operator fun <T> MutableSharedFlow<T>.plusAssign(value: T) = emit(value)