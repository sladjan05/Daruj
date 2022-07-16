package net.jsoft.daruj.common.domain

class LocalUser(
    val name: String,
    val surname: String,
    val sex: Sex,
    val blood: Blood,
    val legalId: String? = null
)