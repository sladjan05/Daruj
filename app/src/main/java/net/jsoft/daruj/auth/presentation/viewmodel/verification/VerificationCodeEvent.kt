package net.jsoft.daruj.auth.presentation.viewmodel.verification

sealed class VerificationCodeEvent {
    class CodeChange(val code: String) : VerificationCodeEvent()
}