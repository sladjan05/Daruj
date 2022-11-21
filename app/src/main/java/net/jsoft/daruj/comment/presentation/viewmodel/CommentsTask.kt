package net.jsoft.daruj.comment.presentation.viewmodel

import net.jsoft.daruj.common.misc.UiText

sealed interface CommentsTask {
    data class ShowError(val message: UiText) : CommentsTask
}