package net.jsoft.daruj.comment.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.exception.DarujException
import net.jsoft.daruj.common.misc.asUiText

class BlankCommentException : DarujException(
    message = "Comment cannot be blank!",
    uiText = R.string.tx_comment_cannot_be_empty.asUiText()
)