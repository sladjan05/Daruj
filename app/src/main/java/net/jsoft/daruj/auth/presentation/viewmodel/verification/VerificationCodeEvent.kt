package net.jsoft.daruj.auth.presentation.viewmodel.verification

sealed class VerificationCodeEvent {
    class NumberClick(val number: Int) : VerificationCodeEvent()
    object DeleteClick : VerificationCodeEvent()

    object ResendVerificationCodeClick : VerificationCodeEvent()
}