package net.jsoft.daruj.common.misc

object RegexPatterns {
    val PhoneNumber = Regex("\\+[0-9]{8,15}")
    val VerificationCode = Regex("[0-9]{6}")
}