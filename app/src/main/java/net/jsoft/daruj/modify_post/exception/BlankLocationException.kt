package net.jsoft.daruj.modify_post.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.exception.AuthException
import net.jsoft.daruj.common.misc.asUiText

class BlankLocationException : AuthException(
    message = "Specified location is empty!",
    uiText = R.string.tx_location_cannot_be_empty.asUiText()
)