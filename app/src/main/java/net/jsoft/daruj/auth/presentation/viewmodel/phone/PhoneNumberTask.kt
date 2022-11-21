package net.jsoft.daruj.auth.presentation.viewmodel.phone

import net.jsoft.daruj.common.misc.UiText

sealed interface PhoneNumberTask {
    object ShowVerificationScreen : PhoneNumberTask
    object ShowCreateAccountScreen : PhoneNumberTask
    object ShowMainScreen : PhoneNumberTask

    data class ShowError(val message: UiText) : PhoneNumberTask
}