package net.jsoft.daruj.common.util

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.misc.asUiText
import java.time.LocalTime
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.minutes

fun formatSMSWaitTime(seconds: Int): UiText {
    val time = LocalTime.ofSecondOfDay(seconds.toLong())
    return "%02d:%02d".format(time.minute, time.second).asUiText()
}

fun formatTimestamp(dateTime: ZonedDateTime): Flow<UiText> = flow {
    val duration = ZonedDateTime.now().nowUTC - dateTime

    val years = duration.inWholeDays / 365
    val months = duration.inWholeDays / 30
    val days = duration.inWholeDays
    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes

    val string = when {
        years != 0L -> R.plurals.tx_years_ago to years
        months != 0L -> R.plurals.tx_months_ago to months
        days != 0L -> R.plurals.tx_days_ago to days
        hours != 0L -> R.plurals.tx_hours_ago to hours
        minutes != 0L -> R.plurals.tx_minutes_ago to minutes
        else -> R.string.tx_just_now to 0
    }

    while (currentCoroutineContext().isActive) {
        val uiText = if (string.second == 0) {
            UiText.StringResource(string.first)
        } else {
            UiText.QuantityString(
                resId = string.first,
                quantity = string.second.toInt(),
                string.second
            )
        }

        emit(uiText)
        delay(1.minutes)
    }
}