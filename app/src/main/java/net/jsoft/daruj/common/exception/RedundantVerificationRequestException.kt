package net.jsoft.daruj.common.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.asUiText

class RedundantVerificationRequestException : AuthException(
    message = "No need for SMS verification!",
    uiText = R.string.tx_verification_successful.asUiText()
)