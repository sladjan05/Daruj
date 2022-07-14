package net.jsoft.daruj.auth.presentation.viewmodel

import net.jsoft.daruj.common.util.UiText

sealed class AuthTask {
    object ShowVerificationScreen : AuthTask()
    object Finish : AuthTask()


    class ShowInfo(val message: UiText) : AuthTask()
    class ShowError(val message: UiText) : AuthTask()
}