package net.jsoft.daruj.common.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Blood(
    val type: Type,
    val rh: RH
) : Parcelable {

    override fun toString(): String {
        return "${type}${rh}"
    }

    enum class Type {
        ZERO,
        A,
        B,
        AB;

        override fun toString(): String {
            return when (this) {
                ZERO -> "0"
                A -> "A"
                B -> "B"
                AB -> "AB"
            }
        }
    }

    enum class RH {
        POSITIVE,
        NEGATIVE;

        override fun toString(): String {
            return when (this) {
                POSITIVE -> "+"
                NEGATIVE -> "-"
            }
        }
    }

    companion object {
        fun fromString(string: String): Blood {
            val type = string.dropLast(1)
            val rh = string.removePrefix(type)

            return Blood(
                type = Type.values().find { it.toString() == type }!!,
                rh = RH.values().find { it.toString() == rh }!!
            )
        }
    }
}