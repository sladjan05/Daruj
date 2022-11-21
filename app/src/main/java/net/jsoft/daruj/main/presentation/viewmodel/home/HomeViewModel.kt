package net.jsoft.daruj.main.presentation.viewmodel.home

import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.domain.usecase.GetLocalUserUseCase
import net.jsoft.daruj.main.domain.usecase.DeletePostUseCase
import net.jsoft.daruj.main.domain.usecase.GetRecommendedPostsUseCase
import net.jsoft.daruj.main.domain.usecase.SetPostSavedUseCase
import net.jsoft.daruj.main.presentation.viewmodel.posts.PostsViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getNextPosts: GetRecommendedPostsUseCase,
    setPostSaved: SetPostSavedUseCase,
    deletePost: DeletePostUseCase,

    getLocalUser: GetLocalUserUseCase
) : PostsViewModel(
    getPosts = getNextPosts::invoke,
    setPostSaved = setPostSaved::invoke,
    deletePost = deletePost::invoke,
    getLocalUser = getLocalUser
)