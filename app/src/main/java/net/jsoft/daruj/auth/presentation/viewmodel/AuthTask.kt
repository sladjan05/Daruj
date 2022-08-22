package net.jsoft.daruj.auth.presentation.viewmodel

import net.jsoft.daruj.common.misc.UiText

sealed class AuthTask {
    class ShowInfo(val message: UiText) : AuthTask()
    class ShowError(val message: UiText) : AuthTask()
}