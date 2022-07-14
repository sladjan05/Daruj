package net.jsoft.daruj.auth.presentation.viewmodel

import android.app.Activity

sealed class AuthEvent {
    class SendVerificationCode(
        val activity: Activity,
        val dialCode: String,
        val phoneNumber: String
    ) : AuthEvent()

    class VerifyWithCode(val code: String) : AuthEvent()
    object SendVerificationCodeAgain : AuthEvent()
}