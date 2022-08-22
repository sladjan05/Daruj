package net.jsoft.daruj.auth.presentation.viewmodel.verification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.screen.Screen
import net.jsoft.daruj.common.domain.repository.AuthRepository
import net.jsoft.daruj.common.domain.usecase.auth.SendSMSVerificationUseCase
import net.jsoft.daruj.common.domain.usecase.auth.VerifyWithCodeUseCase
import net.jsoft.daruj.common.domain.usecase.user.HasCompletedRegistrationUseCase
import net.jsoft.daruj.common.misc.asUiText
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.utils.plusAssign
import net.jsoft.daruj.common.utils.uiText
import javax.inject.Inject

@HiltViewModel
class VerificationCodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

    private val sendSMSVerification: SendSMSVerificationUseCase,
    private val verifyWithCode: VerifyWithCodeUseCase,
    private val hasCompletedRegistration: HasCompletedRegistrationUseCase
) : LoadingViewModel<VerificationCodeEvent, VerificationCodeTask>() {

    var code by mutableStateOf("".asUiText())
        private set

    var waitTime by mutableStateOf(AuthRepository.SMS_WAIT_TIME)
        private set

    var waitTimeProgress by mutableStateOf(1f)
        private set

    private val fullPhoneNumber = savedStateHandle.get<String>(Screen.Verification.FULL_PHONE_NUMBER)!!

    init {
        viewModelScope.registerExceptionHandler { e ->
            mTaskFlow += VerificationCodeTask.ShowError(e.uiText)
        }

        viewModelScope.launch { startSMSWaitTimeCountdown() }
    }

    override fun onEvent(event: VerificationCodeEvent) {
        when (event) {
            is VerificationCodeEvent.NumberClick -> viewModelScope.loadSafely {
                val codeString = code.toString()
                if (codeString.length == 6) return@loadSafely

                val newCode = codeString + event.number.toString()
                code = newCode.asUiText()

                if (newCode.length == 6) {
                    verifyWithCode(code.toString())

                    mTaskFlow += if (hasCompletedRegistration()) {
                        VerificationCodeTask.ShowMainScreen
                    } else {
                        VerificationCodeTask.ShowCreateAccountScreen
                    }
                }
            }

            is VerificationCodeEvent.DeleteClick -> {
                code = code.toString().dropLast(1).asUiText()
            }

            is VerificationCodeEvent.ResendVerificationCodeClick -> viewModelScope.loadSafely {
                sendSMSVerification(fullPhoneNumber)

                mTaskFlow += VerificationCodeTask.ShowInfo(R.string.tx_code_is_sent_again.asUiText())
                startSMSWaitTimeCountdown()
            }
        }
    }

    private suspend fun startSMSWaitTimeCountdown() {
        val step = 50L // ms
        val max = (AuthRepository.SMS_WAIT_TIME * 1000f / step).toInt()

        for (time in max downTo 0) {
            waitTimeProgress = time.toFloat() / max
            waitTime = (AuthRepository.SMS_WAIT_TIME * waitTimeProgress).toInt()
            delay(step)
        }
    }
}