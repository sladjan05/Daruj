package net.jsoft.daruj.common.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.util.asUiText

class TooManyRequestsException : AuthException(
    message = "Too many requests!",
    uiText = R.string.tx_too_many_requests.asUiText()
)