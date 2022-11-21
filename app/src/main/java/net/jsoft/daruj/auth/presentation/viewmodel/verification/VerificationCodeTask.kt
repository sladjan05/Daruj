package net.jsoft.daruj.auth.presentation.viewmodel.verification

import net.jsoft.daruj.common.misc.UiText

sealed interface VerificationCodeTask {
    object ShowCreateAccountScreen : VerificationCodeTask
    object ShowMainScreen : VerificationCodeTask

    data class ShowError(val message: UiText) : VerificationCodeTask
    data class ShowInfo(val message: UiText) : VerificationCodeTask
}