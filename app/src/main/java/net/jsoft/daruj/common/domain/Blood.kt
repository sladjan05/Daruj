package net.jsoft.daruj.common.domain

class Blood(
    val type: Type,
    val rh: RH
) {

    enum class Type {
        ZERO,
        A,
        B,
        AB
    }

    enum class RH {
        POSITIVE,
        NEGATIVE
    }
}