package net.jsoft.daruj.common.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.util.asUiText

class BlankSurameException : AuthException(
    message = "Specified surname is empty!",
    uiText = R.string.tx_surname_cannot_be_empty.asUiText()
)