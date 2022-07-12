package net.jsoft.daruj.auth.presentation.viewmodel.phone

import android.app.Activity
import net.jsoft.daruj.common.util.Country

sealed class PhoneNumberEvent {
    object ExpandCountryDropdown : PhoneNumberEvent()
    class CountryChange(val country: Country?) : PhoneNumberEvent()

    class DialCodeChange(val dialCode: String) : PhoneNumberEvent()
    class PhoneNumberChange(val phoneNumber: String) : PhoneNumberEvent()

    class Next(val activity: Activity) : PhoneNumberEvent()
}