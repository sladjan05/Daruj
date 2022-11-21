package net.jsoft.daruj.main.domain.model

import android.net.Uri
import net.jsoft.daruj.common.domain.model.User
import java.time.ZonedDateTime

data class Receipt(
    val pictureUri: Uri,
    val user: User,
    val timestamp: ZonedDateTime
)