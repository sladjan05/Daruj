package net.jsoft.daruj.common.exception

import net.jsoft.daruj.common.util.UiText

open class AuthException(
    message: String,
    uiText: UiText
) : DarujException(message, uiText)