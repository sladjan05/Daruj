package net.jsoft.daruj.common.util

import android.content.Context
import java.util.*

fun Context.applyLocale(locale: Locale) {
    Locale.setDefault(locale)

    val configuration = resources.configuration
    configuration.setLocale(locale)

    resources.updateConfiguration(configuration, resources.displayMetrics)

    // Možda ovako?     configuration.updateFrom(configuration)
}