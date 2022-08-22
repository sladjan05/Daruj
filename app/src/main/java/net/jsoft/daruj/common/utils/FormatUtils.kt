package net.jsoft.daruj.common.utils

import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.misc.asUiText
import java.time.Clock
import java.time.Instant
import java.time.LocalTime
import java.time.ZonedDateTime

fun formatSMSWaitTime(seconds: Int): UiText {
    val time = LocalTime.ofSecondOfDay(seconds.toLong())
    return "%02d:%02d".format(time.minute, time.second).asUiText()
}

fun formatPostTimestamp(dateTime: ZonedDateTime): UiText {
    val seconds = (Instant.now(Clock.systemUTC()).epochSecond - dateTime.toEpochSecond()).toInt()
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val months = days / 30
    val years = days / 365

    val string = when {
        years != 0 -> R.plurals.tx_years_ago to years
        months != 0 -> R.plurals.tx_months_ago to months
        days != 0 -> R.plurals.tx_days_ago to days
        hours != 0 -> R.plurals.tx_hours_ago to hours
        minutes != 0 -> R.plurals.tx_minutes_ago to minutes
        else -> R.string.tx_just_now to 0
    }

    return if (string.second == 0) {
        UiText.StringResource(string.first)
    } else {
        UiText.QuantityString(
            resId = string.first,
            quantity = string.second,
            string.second
        )
    }
}