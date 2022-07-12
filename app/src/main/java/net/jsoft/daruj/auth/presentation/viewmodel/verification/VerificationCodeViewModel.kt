package net.jsoft.daruj.auth.presentation.viewmodel.verification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.domain.Authenticator
import net.jsoft.daruj.auth.domain.usecase.VerifyWithCodeUseCase
import net.jsoft.daruj.auth.exception.WrongCodeException
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberTask
import net.jsoft.daruj.common.presentation.viewmodel.BasicViewModel
import net.jsoft.daruj.common.util.UiText.Companion.asUiText
import javax.inject.Inject

class VerificationCodeViewModel @Inject constructor(
    private val verifyWithCode: VerifyWithCodeUseCase
) : BasicViewModel<VerificationCodeEvent, VerificationCodeTask>() {

    var code by mutableStateOf("".asUiText())
        private set

    override fun onEvent(event: VerificationCodeEvent) {
        when (event) {
            is VerificationCodeEvent.CodeChange -> {
                code = event.code.asUiText()
            }

            is VerificationCodeEvent.SendVerificationCodeAgain -> viewModelScope.launch {
                _taskFlow.emit(VerificationCodeTask.SendVerificationCodeAgain)
            }

            is VerificationCodeEvent.Next -> viewModelScope.launch(
                context = CoroutineExceptionHandler { _, throwable -> 
                    when(throwable) {
                        is WrongCodeException -> viewModelScope.launch {
                            _taskFlow.emit(VerificationCodeTask.ShowError(R.string.tx_invalid_code.asUiText()))
                        }

                        else -> viewModelScope.launch {
                            _taskFlow.emit(VerificationCodeTask.ShowError(R.string.tx_unknown_error.asUiText()))
                        }
                    }
                }
            ) {
                verifyWithCode(code.toString())
                _taskFlow.emit(VerificationCodeTask.Next)
            }
        }
    }
}