package net.jsoft.daruj.modify_post.presentation.viewmodel

import net.jsoft.daruj.common.misc.UiText

sealed interface ModifyPostTask {
    object Close : ModifyPostTask

    data class ShowError(val message: UiText) : ModifyPostTask
}