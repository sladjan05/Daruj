package net.jsoft.daruj.common.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.util.asUiText

class BlankNameException : AuthException(
    message = "Specified name is empty!",
    uiText = R.string.tx_name_cannot_be_empty.asUiText()
)