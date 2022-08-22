package net.jsoft.daruj.auth.presentation.viewmodel.phone

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.domain.repository.AuthRepository
import net.jsoft.daruj.common.domain.usecase.auth.InitializeAuthenticatorUseCase
import net.jsoft.daruj.common.domain.usecase.auth.SendSMSVerificationUseCase
import net.jsoft.daruj.common.domain.usecase.user.HasCompletedRegistrationUseCase
import net.jsoft.daruj.common.misc.Country
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.misc.asUiText
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.utils.plusAssign
import net.jsoft.daruj.common.utils.uiText
import javax.inject.Inject

@HiltViewModel
class PhoneNumberViewModel @Inject constructor(
    private val initializeAuthenticator: InitializeAuthenticatorUseCase,
    private val sendSMSVerification: SendSMSVerificationUseCase,

    private val hasCompletedRegistration: HasCompletedRegistrationUseCase
) : LoadingViewModel<PhoneNumberEvent, PhoneNumberTask>() {

    var country: Country? by mutableStateOf(Country.BA)
        private set

    var countryExpanded by mutableStateOf(false)
        private set

    var dialCode by mutableStateOf(
        Country.BA.dialCode.removePrefix("+").asUiText()
    )
        private set

    var phoneNumber by mutableStateOf("".asUiText())
        private set

    val fullPhoneNumber: UiText
        get() = "+$dialCode$phoneNumber".asUiText()

    init {
        viewModelScope.registerExceptionHandler { e ->
            mTaskFlow += PhoneNumberTask.ShowError(e.uiText)
        }
    }

    override fun onEvent(event: PhoneNumberEvent) {
        when (event) {
            is PhoneNumberEvent.Dismiss -> setExpanded()

            is PhoneNumberEvent.CountryClick -> setExpanded(country = !countryExpanded)
            is PhoneNumberEvent.CountryChange -> {
                country = event.country
                setExpanded()

                if (country != null) {
                    dialCode = country!!.dialCode.removePrefix("+").asUiText()
                }
            }

            is PhoneNumberEvent.DialCodeChange -> {
                if (!event.dialCode.isDigitsOnly()) return

                dialCode = event.dialCode.asUiText()
                country = Country.values().find { country ->
                    country.dialCode == "+" + event.dialCode
                }
            }

            is PhoneNumberEvent.PhoneNumberChange -> {
                if (!event.phoneNumber.isDigitsOnly()) return

                phoneNumber = event.phoneNumber.asUiText()
            }

            is PhoneNumberEvent.SendVerificationCodeClick -> viewModelScope.loadSafely {
                initializeAuthenticator(Activity::class to event.activity)

                when (sendSMSVerification(fullPhoneNumber.toString())) {
                    AuthRepository.State.SENT_SMS -> mTaskFlow += PhoneNumberTask.ShowVerificationScreen
                    AuthRepository.State.AUTHENTICATED -> {
                        mTaskFlow += if (hasCompletedRegistration()) {
                            PhoneNumberTask.ShowMainScreen
                        } else {
                            PhoneNumberTask.ShowCreateAccountScreen
                        }
                    }
                }
            }
        }
    }

    private fun setExpanded(
        country: Boolean = false
    ) {
        countryExpanded = country
    }
}