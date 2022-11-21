package net.jsoft.daruj.common.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Keep
@Parcelize
data class DonationRecord(
    val postId: String,
    val timestamp: ZonedDateTime
) : Parcelable