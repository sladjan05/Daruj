package net.jsoft.daruj.common.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.util.asUiText

class MissingArgumentException : AuthException(
    message = "Not enough arguments to initialize the authenticator!",
    uiText = R.string.tx_internal_error.asUiText()
)