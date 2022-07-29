package net.jsoft.daruj.common.util

object RegexPatterns {
    val PHONE_NUMBER = Regex("\\+[0-9]{8,15}")
    val VERIFICATION_CODE = Regex("[0-9]{6}")
}