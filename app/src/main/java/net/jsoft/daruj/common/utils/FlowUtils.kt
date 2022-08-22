package net.jsoft.daruj.common.utils

import kotlinx.coroutines.flow.MutableSharedFlow

suspend operator fun <T> MutableSharedFlow<T>.plusAssign(value: T) = emit(value)