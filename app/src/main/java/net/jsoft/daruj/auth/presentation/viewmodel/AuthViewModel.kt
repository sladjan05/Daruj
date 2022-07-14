package net.jsoft.daruj.auth.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.domain.usecase.InitializeAuthenticatorUseCase
import net.jsoft.daruj.auth.domain.usecase.SendSMSVerificationUseCase
import net.jsoft.daruj.auth.domain.usecase.VerifyWithCodeUseCase
import net.jsoft.daruj.auth.exception.InvalidRequestException
import net.jsoft.daruj.auth.exception.RedundantVerificationRequestException
import net.jsoft.daruj.auth.exception.TooManyRequestsException
import net.jsoft.daruj.auth.exception.WrongCodeException
import net.jsoft.daruj.auth.presentation.screen.Screen
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.UiText.Companion.asUiText
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val initializeAuthenticator: InitializeAuthenticatorUseCase,
    private val sendSMSVerification: SendSMSVerificationUseCase,
    private val verifyWithCode: VerifyWithCodeUseCase
) : LoadingViewModel<AuthEvent, AuthTask>() {

    var screen: Screen by mutableStateOf(Screen.Phone)
        private set

    private lateinit var fullPhoneNumber: String

    override fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SendVerificationCode -> viewModelScope.launch(smsExceptionHandler) {
                fullPhoneNumber = "+${event.dialCode}${event.phoneNumber}"

                load {
                    initializeAuthenticator(event.activity)
                    sendSMSVerification(fullPhoneNumber)
                }

                screen = Screen.Verification
                mTaskFlow.emit(AuthTask.ShowVerificationScreen)
            }


            is AuthEvent.SendVerificationCodeAgain -> viewModelScope.launch(smsExceptionHandler) {
                load { sendSMSVerification(fullPhoneNumber) }
            }

            is AuthEvent.VerifyWithCode -> viewModelScope.launch(verificationExceptionHandler) {
                load { verifyWithCode(event.code) }
                mTaskFlow.emit(AuthTask.Finish)
            }
        }
    }

    // ============================== EXCEPTION HANDLERS ==============================

    private val smsExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        isLoading = false

        when (throwable) {
            is RedundantVerificationRequestException -> {
                Log.d("Auth", "RedundantVerificationRequest")
            }

            is InvalidRequestException -> viewModelScope.launch {
                mTaskFlow.emit(AuthTask.ShowError(R.string.tx_invalid_phone_number.asUiText()))
            }

            is TooManyRequestsException -> viewModelScope.launch {
                mTaskFlow.emit(AuthTask.ShowError(R.string.tx_too_many_requests.asUiText()))
            }

            else -> viewModelScope.launch {
                mTaskFlow.emit(AuthTask.ShowError(R.string.tx_unknown_error.asUiText()))
            }
        }
    }

    private val verificationExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        isLoading = false

        when (throwable) {
            is WrongCodeException -> viewModelScope.launch {
                mTaskFlow.emit(AuthTask.ShowError(R.string.tx_invalid_code.asUiText()))
            }

            else -> viewModelScope.launch {
                mTaskFlow.emit(AuthTask.ShowError(R.string.tx_unknown_error.asUiText()))
            }
        }
    }
}