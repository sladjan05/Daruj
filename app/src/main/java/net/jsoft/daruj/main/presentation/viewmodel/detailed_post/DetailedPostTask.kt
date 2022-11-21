package net.jsoft.daruj.main.presentation.viewmodel.detailed_post

import net.jsoft.daruj.common.misc.UiText

sealed interface DetailedPostTask {
    object Finish : DetailedPostTask

    data class ShowError(val message: UiText) : DetailedPostTask
}