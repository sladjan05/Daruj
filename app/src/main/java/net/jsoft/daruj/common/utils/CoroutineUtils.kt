package net.jsoft.daruj.common.utils

import kotlinx.coroutines.Deferred

suspend fun <T> Deferred<T>.awaitOrNull() =
    try {
        await()
    } catch (e: Exception) {
        null
    }