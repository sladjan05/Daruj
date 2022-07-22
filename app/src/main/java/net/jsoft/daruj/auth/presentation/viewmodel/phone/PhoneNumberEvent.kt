package net.jsoft.daruj.auth.presentation.viewmodel.phone

import net.jsoft.daruj.common.util.Country

sealed class PhoneNumberEvent {
    object CountryClick : PhoneNumberEvent()
    object CountryDismiss : PhoneNumberEvent()
    class CountryChange(val country: Country?) : PhoneNumberEvent()

    class DialCodeChange(val dialCode: String) : PhoneNumberEvent()
    class PhoneNumberChange(val phoneNumber: String) : PhoneNumberEvent()
}