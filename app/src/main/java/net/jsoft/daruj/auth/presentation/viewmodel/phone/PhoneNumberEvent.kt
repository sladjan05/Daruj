package net.jsoft.daruj.auth.presentation.viewmodel.phone

import android.app.Activity
import net.jsoft.daruj.common.misc.Country

sealed class PhoneNumberEvent {
    object Dismiss : PhoneNumberEvent()

    object CountryClick : PhoneNumberEvent()
    class CountryChange(val country: Country?) : PhoneNumberEvent()

    class DialCodeChange(val dialCode: String) : PhoneNumberEvent()
    class PhoneNumberChange(val phoneNumber: String) : PhoneNumberEvent()

    class SendVerificationCodeClick(val activity: Activity) : PhoneNumberEvent()
}