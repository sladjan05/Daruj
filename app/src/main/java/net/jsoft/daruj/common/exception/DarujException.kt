package net.jsoft.daruj.common.exception

import net.jsoft.daruj.common.util.UiText

open class DarujException(
    message: String,
    val uiText: UiText
) : Exception(message)