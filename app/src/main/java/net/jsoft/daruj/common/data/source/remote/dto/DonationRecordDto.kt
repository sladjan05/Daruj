package net.jsoft.daruj.common.data.source.remote.dto

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import net.jsoft.daruj.common.domain.model.DonationRecord
import java.time.ZoneId
import java.time.ZonedDateTime

@Keep
class DonationRecordDto(
    var postId: String? = null,
    var timestamp: Timestamp? = null
) {
    fun getModel() = DonationRecord(
        postId = postId!!,
        timestamp = ZonedDateTime.ofInstant(
            timestamp!!.toDate().toInstant(),
            ZoneId.of("UTC")
        )
    )
}