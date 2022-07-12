package net.jsoft.daruj.common.util

import net.jsoft.daruj.common.exception.DarujException

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val exception: DarujException) : Resource<T>()
}