package net.jsoft.daruj.main.presentation.screen.viewmodel.posts

import net.jsoft.daruj.main.domain.model.Post

interface PostsEvent {
    object Refresh : PostsEvent

    class SaveClick(val post: Post) : PostsEvent

    object ReachedEnd : PostsEvent
}