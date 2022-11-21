package net.jsoft.daruj.main.domain.model

import java.time.ZonedDateTime

data class Donation(
    val post: Post,
    val timestamp: ZonedDateTime
)