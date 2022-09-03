package net.jsoft.daruj.main.presentation.screen.viewmodel.posts

import net.jsoft.daruj.common.misc.UiText

interface PostsTask {
    class ShowError(val message: UiText) : PostsTask
}