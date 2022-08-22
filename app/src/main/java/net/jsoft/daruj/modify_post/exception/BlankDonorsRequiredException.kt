package net.jsoft.daruj.modify_post.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.exception.AuthException
import net.jsoft.daruj.common.misc.asUiText

class BlankDonorsRequiredException : AuthException(
    message = "Specified donors required field is empty!",
    uiText = R.string.tx_donors_required_cannot_be_empty.asUiText()
)