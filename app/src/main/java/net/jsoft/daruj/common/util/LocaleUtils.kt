package net.jsoft.daruj.common.util

import android.content.Context
import java.util.*

fun Context.setLocale(locale: Locale) {
    Locale.setDefault(locale)

    val displayMetrics = resources.displayMetrics
    val configuration = resources.configuration
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, displayMetrics)
}