package net.jsoft.daruj.common.util

import net.jsoft.daruj.R
import net.jsoft.daruj.common.exception.DarujException
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.misc.asUiText

val Exception.uiText: UiText
    get() {
        printStackTrace()
        return (this as? DarujException)?.uiText ?: R.string.tx_unknown_error.asUiText()
    }