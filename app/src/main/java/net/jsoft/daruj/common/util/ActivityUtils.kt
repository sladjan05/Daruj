package net.jsoft.daruj.common.util

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T> Context.switchActivity() {
    val intent = Intent(
        this,
        T::class.java
    )

    startActivity(intent)
    (this as Activity).finish()
}