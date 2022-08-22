package net.jsoft.daruj.auth.presentation.viewmodel.phone

import net.jsoft.daruj.common.misc.UiText

sealed class PhoneNumberTask {
    object ShowVerificationScreen : PhoneNumberTask()
    object ShowCreateAccountScreen : PhoneNumberTask()
    object ShowMainScreen : PhoneNumberTask()

    class ShowInfo(val message: UiText) : PhoneNumberTask()
    class ShowError(val message: UiText) : PhoneNumberTask()
}