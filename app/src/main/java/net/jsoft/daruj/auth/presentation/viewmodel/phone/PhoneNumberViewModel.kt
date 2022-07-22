package net.jsoft.daruj.auth.presentation.viewmodel.phone

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import net.jsoft.daruj.common.presentation.viewmodel.BasicViewModel
import net.jsoft.daruj.common.util.Country
import net.jsoft.daruj.common.util.asUiText

class PhoneNumberViewModel : BasicViewModel<PhoneNumberEvent, Nothing>() {

    var country: Country? by mutableStateOf(Country.BOSNIA_AND_HERZEGOVINA)
        private set

    var countryDropdownExpanded by mutableStateOf(false)
        private set

    var dialCode by mutableStateOf(
        Country.BOSNIA_AND_HERZEGOVINA.dialCode.removePrefix("+").asUiText()
    )
        private set

    var phoneNumber by mutableStateOf("".asUiText())
        private set

    override fun onEvent(event: PhoneNumberEvent) {
        when (event) {
            is PhoneNumberEvent.CountryClick -> setExpanded(country = true)
            is PhoneNumberEvent.CountryDismiss -> setExpanded(country = false)
            is PhoneNumberEvent.CountryChange -> {
                country = event.country
                setExpanded()

                if (country != null) {
                    dialCode = country!!.dialCode.removePrefix("+").asUiText()
                }
            }

            is PhoneNumberEvent.DialCodeChange -> {
                dialCode = event.dialCode.asUiText()
                country = Country.values().find { country ->
                    country.dialCode == "+" + event.dialCode
                }
            }

            is PhoneNumberEvent.PhoneNumberChange -> phoneNumber = event.phoneNumber.asUiText()
        }
    }

    private fun setExpanded(
        country: Boolean = false
    ) {
        countryDropdownExpanded = country
    }
}