package net.jsoft.daruj.common.utils

import kotlinx.coroutines.Deferred

suspend fun <T> Deferred<T>.awaitOrNull(): T? {
    return try {
        await()
    } catch (e: Exception) {
        null
    }
}