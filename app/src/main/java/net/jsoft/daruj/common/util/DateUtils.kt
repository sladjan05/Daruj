package net.jsoft.daruj.common.util

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

// FIXME: Should be extending companion
val ZonedDateTime.nowUTC: ZonedDateTime
    get() {
        val clock = Clock.systemUTC()
        val instant = Instant.now(clock)
        val zoneId = ZoneId.of("UTC")

        return ZonedDateTime.ofInstant(instant, zoneId)
    }

operator fun ZonedDateTime.minus(other: ZonedDateTime): Duration {
    val thisEpochSecond = this.toEpochSecond()
    val otherEpochSecond = other.toEpochSecond()

    val secondDifference = thisEpochSecond - otherEpochSecond
    return secondDifference.seconds
}