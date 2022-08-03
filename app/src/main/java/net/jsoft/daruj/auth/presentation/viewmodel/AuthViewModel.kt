package net.jsoft.daruj.auth.presentation.viewmodel

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.domain.Authenticator
import net.jsoft.daruj.auth.domain.usecase.InitializeAuthenticatorUseCase
import net.jsoft.daruj.auth.domain.usecase.SendSMSVerificationUseCase
import net.jsoft.daruj.auth.domain.usecase.VerifyWithCodeUseCase
import net.jsoft.daruj.auth.presentation.screen.Screen
import net.jsoft.daruj.common.domain.usecase.GetLocalSettingsUseCase
import net.jsoft.daruj.common.domain.usecase.UpdateLocalSettingsUseCase
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
    private val updateLocalSettings: UpdateLocalSettingsUseCase,
) : LoadingViewModel<AuthEvent, AuthTask>() {

    var screen: Screen by mutableStateOf(Screen.Phone)
        private set

    var waitTime by mutableStateOf(Authenticator.SMS_WAIT_TIME)
        private set

    var waitTimeProgress by mutableStateOf(1f)
        private set

    private lateinit var fullPhoneNumber: String

    init {
        viewModelScope.registerExceptionHandler { e ->
            mTaskFlow += AuthTask.ShowError(e.uiText)
        }

        viewModelScope.launch {
            loadSafely {
                val settings = getLocalSettings()
                val newSettings = settings.copy(
                    hasCompletedIntroduction = true
                )

                updateLocalSettings(newSettings)
            }
        }
    }

    override fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SendVerificationCodeClick -> viewModelScope.launch {
                fullPhoneNumber = "+${event.dialCode}${event.phoneNumber}"

                loadSafely {
                    initializeAuthenticator(Activity::class to event.activity)
                    sendSMSVerification(fullPhoneNumber)
                } ifSuccessful { state ->
                    when (state) {
                        Authenticator.State.SENT_SMS -> {
                            screen = Screen.Verification
                            mTaskFlow += AuthTask.ShowVerificationScreen
                            startSMSWaitTimeCountdown()
                        }

                        Authenticator.State.AUTHENTICATED -> mTaskFlow += AuthTask.Finish
                    }
                }
            }


            is AuthEvent.ResendVerificationCodeClick -> viewModelScope.launch {
                loadSafely {
                    sendSMSVerification(fullPhoneNumber)
                } ifSuccessful {
                    mTaskFlow += AuthTask.ShowInfo(R.string.tx_code_is_sent_again.asUiText())
                    startSMSWaitTimeCountdown()
                }
            }

            is AuthEvent.VerifyWithCode -> viewModelScope.launch {
                loadSafely {
                    verifyWithCode(event.code)
                } ifSuccessful {
                    mTaskFlow += AuthTask.Finish
                }
            }
        }
    }

    private suspend fun startSMSWaitTimeCountdown() = withContext(dispatcherProvider.main) {
        val step = 50L // ms
        val max = (Authenticator.SMS_WAIT_TIME * 1000f / step).toInt()

        for (time in max downTo 0) {
            waitTimeProgress = time.toFloat() / max
            waitTime = (Authenticator.SMS_WAIT_TIME * waitTimeProgress).toInt()
            delay(step)
        }
    }
}