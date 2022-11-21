package net.jsoft.daruj.auth.presentation.viewmodel.verification

sealed interface VerificationCodeEvent {
    data class NumberClick(val number: Int) : VerificationCodeEvent
    object DeleteClick : VerificationCodeEvent

    object ResendVerificationCodeClick : VerificationCodeEvent
}