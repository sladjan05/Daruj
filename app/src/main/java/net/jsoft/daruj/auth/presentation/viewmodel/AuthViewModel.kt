package net.jsoft.daruj.auth.presentation.viewmodel

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.domain.usecase.InitializeAuthenticatorUseCase
import net.jsoft.daruj.auth.domain.usecase.SendSMSVerificationUseCase
import net.jsoft.daruj.auth.domain.usecase.VerifyWithCodeUseCase
import net.jsoft.daruj.auth.presentation.screen.Screen
import net.jsoft.daruj.common.domain.Authenticator
import net.jsoft.daruj.common.domain.usecase.GetLocalSettingsUseCase
import net.jsoft.daruj.common.domain.usecase.UpdateLocalSettingsUseCase
import net.jsoft.daruj.common.exception.RedundantVerificationRequestException
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.asUiText
import net.jsoft.daruj.common.util.plusAssign
import net.jsoft.daruj.common.util.uiText
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val initializeAuthenticator: InitializeAuthenticatorUseCase,
    private val sendSMSVerification: SendSMSVerificationUseCase,
    private val verifyWithCode: VerifyWithCodeUseCase,

    private val getLocalSettings: GetLocalSettingsUseCase,
    private val updateLocalSettings: UpdateLocalSettingsUseCase
) : LoadingViewModel<AuthEvent, AuthTask>() {

    var screen: Screen by mutableStateOf(Screen.Phone)
        private set

    var waitTime by mutableStateOf(1)
        private set

    var waitTimeProgress by mutableStateOf(0.0f)
        private set

    private lateinit var fullPhoneNumber: String

    init {
        viewModelScope.launch {
            val settings = getLocalSettings()
            val newSettings = settings.copy(
                hasCompletedIntroduction = true
            )

            updateLocalSettings(newSettings)
        }
    }

    override fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SendVerificationCodeClick -> viewModelScope.launch(verificationExceptionHandler) {
                fullPhoneNumber = "+${event.dialCode}${event.phoneNumber}"

                load {
                    initializeAuthenticator(Activity::class to event.activity)
                    sendSMSVerification(fullPhoneNumber)
                }

                screen = Screen.Verification
                mTaskFlow += AuthTask.ShowVerificationScreen
                startSMSWaitTimeCountdown()
            }


            is AuthEvent.SendVerificationCodeAgainClick -> viewModelScope.launch(verificationExceptionHandler) {
                load { sendSMSVerification(fullPhoneNumber) }

                mTaskFlow += AuthTask.ShowInfo(R.string.tx_code_is_sent_again.asUiText())
                startSMSWaitTimeCountdown()
            }

            is AuthEvent.VerifyWithCodeClick -> viewModelScope.launch(smsExceptionHandler) {
                load { verifyWithCode(event.code) }
                mTaskFlow += AuthTask.Finish
            }
        }
    }

    private suspend fun startSMSWaitTimeCountdown() {
        val step = 20L // ms
        val max = Authenticator.SMS_WAIT_TIME * step

        for (time in max downTo 0) {
            waitTimeProgress = time.toFloat() / max
            waitTime = (Authenticator.SMS_WAIT_TIME * waitTimeProgress).toInt()
            delay(step)
        }
    }

    // ======================================== EXCEPTION HANDLERS ========================================

    private val verificationExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        isLoading = false

        if (throwable is RedundantVerificationRequestException) viewModelScope.launch {
            mTaskFlow += AuthTask.Finish
            return@launch
        }

        viewModelScope.launch {
            mTaskFlow += AuthTask.ShowError(throwable.uiText)
        }
    }

    private val smsExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        isLoading = false

        viewModelScope.launch {
            mTaskFlow += AuthTask.ShowError(throwable.uiText)
        }
    }
}