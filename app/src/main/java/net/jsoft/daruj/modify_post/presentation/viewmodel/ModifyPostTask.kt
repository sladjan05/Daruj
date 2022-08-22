package net.jsoft.daruj.modify_post.presentation.viewmodel

import net.jsoft.daruj.common.misc.UiText

sealed class ModifyPostTask {
    object Close : ModifyPostTask()

    class ShowError(val message: UiText) : ModifyPostTask()
    class ShowInfo(val message: UiText) : ModifyPostTask()
}