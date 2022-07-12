package net.jsoft.daruj.auth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.jsoft.daruj.auth.domain.Authenticator
import net.jsoft.daruj.auth.presentation.screen.Screen
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberEvent
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberTask
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberViewModel
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeEvent
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeTask
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeViewModel
import net.jsoft.daruj.common.presentation.viewmodel.BasicViewModel
import net.jsoft.daruj.common.util.UiText.Companion.asUiText
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val phoneViewModel: PhoneNumberViewModel,
    val verificationViewModel: VerificationCodeViewModel,

    private val authenticator: Authenticator
) : BasicViewModel<AuthEvent, AuthTask>() {
    var screen: Screen = Screen.Phone
        private set

    init {
        viewModelScope.launch {
            phoneViewModel.taskFlow.collectLatest { task ->
                when (task) {
                    is PhoneNumberTask.Next -> {
                        screen = Screen.Verification
                        _taskFlow.emit(AuthTask.Next)
                    }

                    is PhoneNumberTask.ShowError -> {
                        _taskFlow.emit(AuthTask.ShowError(task.message))
                    }

                    is PhoneNumberTask.ShowInfo -> TODO()
                }
            }
        }

        viewModelScope.launch {
            verificationViewModel.taskFlow.collectLatest { task ->
                when (task) {
                    is VerificationCodeTask.Next -> {
                        _taskFlow.emit(AuthTask.ShowInfo("Uspješna prijava!".asUiText()))
                    }

                    VerificationCodeTask.SendVerificationCodeAgain -> viewModelScope.launch {
                        TODO("Wrong phone format")
                        authenticator.sendSMSVerification(phoneViewModel.phoneNumber.toString())
                    }

                    is VerificationCodeTask.ShowError -> {
                        _taskFlow.emit(AuthTask.ShowError(task.message))
                    }

                    is VerificationCodeTask.ShowInfo -> TODO()
                }
            }
        }
    }

    override fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Next -> when (screen) {
                is Screen.Phone -> {
                    phoneViewModel.onEvent(PhoneNumberEvent.Next(event.activity))
                }

                is Screen.Verification -> {
                    verificationViewModel.onEvent(VerificationCodeEvent.Next)
                }
            }
        }
    }
}