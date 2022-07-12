package net.jsoft.daruj.auth.presentation.viewmodel

import android.app.Activity

sealed class AuthEvent {
    class Next(val activity: Activity) : AuthEvent()
}