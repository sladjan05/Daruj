package net.jsoft.daruj.common.util

object RegexPatterns {
    val PHONE_NUMBER_REGEX = Regex("\\+[0-9]{8,15}")
    val VERIFICATION_CODE_REGEX = Regex("[0-9]{6}")
}
