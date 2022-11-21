package net.jsoft.daruj.common.util

import kotlinx.coroutines.Deferred

suspend fun <T> Deferred<T>.awaitOrNull(): T? {
    return try {
        await()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

suspend fun Deferred<*>.safeAwait() {
    try {
        await()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}