package net.jsoft.daruj.common.domain.model

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import net.jsoft.daruj.common.util.minus
import net.jsoft.daruj.common.util.nowUTC
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.days

@Keep
@Parcelize
data class LocalUser(
    val mutable: Mutable,
    val immutable: Immutable,
    val data: Data
) : Parcelable {

    @Keep
    @Parcelize
    data class Mutable(
        val name: String,
        val surname: String,
        val sex: Sex,
        val blood: Blood,
        val isPrivate: Boolean
    ) : Parcelable

    @Keep
    @Parcelize
    data class Immutable(
        val savedPosts: List<String>,
        val donations: List<DonationRecord>,
        val points: Int
    ) : Parcelable

    @Keep
    @Parcelize
    data class Data(
        val id: String,
        val pictureUri: Uri?
    ) : Parcelable
}

val LocalUser.displayName: String
    get() =
        if (mutable.isPrivate)
            "Korisnik " + data.id.substring(0, 5).uppercase()
        else
            mutable.name + " " + mutable.surname

val LocalUser.daysUntilRecovery: Int
    get() {
        val lastDonation = immutable.donations.lastOrNull() ?: return 0
        val lastDonationTimestamp = lastDonation.timestamp
        val duration = ZonedDateTime.now().nowUTC - lastDonationTimestamp

        val regenDurationDays = when (mutable.sex) {
            Sex.Male -> 90.days
            Sex.Female -> 120.days
        }

        return (regenDurationDays - duration).inWholeDays.toInt()
    }

val LocalUser.canDonateBlood: Boolean
    get() = daysUntilRecovery <= 0