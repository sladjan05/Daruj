package net.jsoft.daruj.common.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.asUiText

class WrongCodeException : AuthException(
    message = "Supplied verification code is not valid!",
    uiText = R.string.tx_invalid_code.asUiText()
)