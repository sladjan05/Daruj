package net.jsoft.daruj.modify_post.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.exception.AuthException
import net.jsoft.daruj.common.misc.asUiText

class BlankParentNameException : AuthException(
    message = "Specified parent name is empty!",
    uiText = R.string.tx_parent_name_cannot_be_empty.asUiText()
)