package net.jsoft.daruj.common.util

import net.jsoft.daruj.R
import net.jsoft.daruj.common.exception.DarujException

val Throwable.uiText: UiText
    get() {
        printStackTrace()
        return (this as? DarujException)?.uiText ?: R.string.tx_unknown_error.asUiText()
    }