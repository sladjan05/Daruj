package net.jsoft.daruj.auth.presentation.viewmodel.verification

import net.jsoft.daruj.common.util.UiText

sealed class VerificationCodeTask {
    object Next : VerificationCodeTask()

    object SendVerificationCodeAgain : VerificationCodeTask()

    class ShowInfo(val message: UiText) : VerificationCodeTask()
    class ShowError(val message: UiText) : VerificationCodeTask()
}