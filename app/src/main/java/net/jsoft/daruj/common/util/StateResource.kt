package net.jsoft.daruj.common.util

import net.jsoft.daruj.common.exception.DarujException

sealed class StateResource {
    object Success : StateResource()
    class Error(val exception: DarujException) : StateResource()
}