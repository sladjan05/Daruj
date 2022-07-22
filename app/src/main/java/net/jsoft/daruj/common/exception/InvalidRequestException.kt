package net.jsoft.daruj.common.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.util.asUiText

class InvalidRequestException : AuthException(
    message = "Verification request is not valid!",
    uiText = R.string.tx_invalid_phone_number.asUiText()
)