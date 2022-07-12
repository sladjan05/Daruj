package net.jsoft.daruj.auth.presentation.viewmodel.phone

import net.jsoft.daruj.common.util.UiText

sealed class PhoneNumberTask {
    object Next : PhoneNumberTask()

    class ShowInfo(val message: UiText) : PhoneNumberTask()
    class ShowError(val message: UiText) : PhoneNumberTask()
}