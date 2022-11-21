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

    fun canDonateTo(other: Blood): Boolean {
        if(rh == RH.Positive && other.rh == RH.Negative) return false

        return when {
            type == Type.Zero -> true
            type == Type.A && other.type in arrayOf(Type.A, Type.AB) -> true
            type == Type.B && other.type in arrayOf(Type.B, Type.AB) -> true
            type == Type.AB && other.type == Type.AB -> true

            else -> false
        }
    }

    override fun toString(): String {
        return "$type$rh"
    }

    enum class Type {
        Zero,
        A,
        B,
        AB;

        override fun toString(): String {
            return when (this) {
                Zero -> "0"
                A -> "A"
                B -> "B"
                AB -> "AB"
            }
        }
    }

    enum class RH {
        Positive,
        Negative;

        override fun toString(): String {
            return when (this) {
                Positive -> "+"
                Negative -> "-"
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