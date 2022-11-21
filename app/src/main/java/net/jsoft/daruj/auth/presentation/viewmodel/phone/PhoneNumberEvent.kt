package net.jsoft.daruj.auth.presentation.viewmodel.phone

import android.app.Activity
import net.jsoft.daruj.common.misc.Country

sealed interface PhoneNumberEvent {
    object Dismiss : PhoneNumberEvent

    object CountryClick : PhoneNumberEvent
    data class CountryChange(val country: Country?) : PhoneNumberEvent

    data class DialCodeChange(val dialCode: String) : PhoneNumberEvent
    data class PhoneNumberChange(val phoneNumber: String) : PhoneNumberEvent

    data class SendVerificationCodeClick(val activity: Activity) : PhoneNumberEvent
}