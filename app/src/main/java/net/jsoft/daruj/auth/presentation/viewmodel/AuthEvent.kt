package net.jsoft.daruj.auth.presentation.viewmodel

import android.app.Activity

sealed class AuthEvent {
    class SendVerificationCodeClick(
        val activity: Activity,
        val dialCode: String,
        val phoneNumber: String
    ) : AuthEvent()

    class VerifyWithCodeClick(val code: String) : AuthEvent()
    object SendVerificationCodeAgainClick : AuthEvent()
}