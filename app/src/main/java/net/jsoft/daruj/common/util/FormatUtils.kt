package net.jsoft.daruj.common.util

import java.time.LocalTime

fun formatSMSWaitTime(seconds: Int): String {
    val time = LocalTime.ofSecondOfDay(seconds.toLong())
    return "%02d:%02d".format(time.minute, time.second)
}