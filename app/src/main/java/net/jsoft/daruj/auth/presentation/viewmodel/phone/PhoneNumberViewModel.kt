package net.jsoft.daruj.auth.presentation.viewmodel.phone

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.domain.usecase.InitializeAuthenticatorUseCase
import net.jsoft.daruj.auth.domain.usecase.SendSMSVerificationUseCase
import net.jsoft.daruj.auth.exception.InvalidRequestException
import net.jsoft.daruj.auth.exception.RedundantVerificationRequestException
import net.jsoft.daruj.auth.exception.TooManyRequestsException
import net.jsoft.daruj.common.presentation.viewmodel.BasicViewModel
import net.jsoft.daruj.common.util.Country
import net.jsoft.daruj.common.util.UiText.Companion.asUiText
import javax.inject.Inject

class PhoneNumberViewModel @Inject constructor(
    private val initializeAuthenticator: InitializeAuthenticatorUseCase,
    private val sendSMSVerification: SendSMSVerificationUseCase
) : BasicViewModel<PhoneNumberEvent, PhoneNumberTask>() {

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
            is PhoneNumberEvent.ExpandCountryDropdown -> {
                countryDropdownExpanded = !countryDropdownExpanded
            }

            is PhoneNumberEvent.CountryChange -> {
                country = event.country
                countryDropdownExpanded = false

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

            is PhoneNumberEvent.PhoneNumberChange -> {
                phoneNumber = event.phoneNumber.asUiText()
            }

            is PhoneNumberEvent.Next -> viewModelScope.launch(
                context = CoroutineExceptionHandler { _, throwable ->
                    when (throwable) {
                        is RedundantVerificationRequestException -> {
                            Log.d("Auth", "RedundantVerificationRequest")
                        }

                        is InvalidRequestException -> viewModelScope.launch {
                            _taskFlow.emit(PhoneNumberTask.ShowError(R.string.tx_invalid_phone_number.asUiText()))
                        }

                        is TooManyRequestsException -> viewModelScope.launch {
                            _taskFlow.emit(PhoneNumberTask.ShowError(R.string.tx_too_many_requests.asUiText()))
                        }

                        else -> viewModelScope.launch {
                            throwable.printStackTrace()
                            _taskFlow.emit(PhoneNumberTask.ShowError(R.string.tx_unknown_error.asUiText()))
                        }
                    }
                }
            ) {
                initializeAuthenticator(event.activity)
                sendSMSVerification("+$dialCode$phoneNumber")
                _taskFlow.emit(PhoneNumberTask.Next)
            }
        }
    }
}