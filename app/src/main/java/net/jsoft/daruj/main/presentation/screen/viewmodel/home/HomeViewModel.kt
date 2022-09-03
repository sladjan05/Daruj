package net.jsoft.daruj.main.presentation.screen.viewmodel.home

import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.main.domain.usecase.GetRecommendedPostsUseCase
import net.jsoft.daruj.main.domain.usecase.SetPostSavedUseCase
import net.jsoft.daruj.main.presentation.screen.viewmodel.posts.PostsViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getNextPosts: GetRecommendedPostsUseCase,
    setPostSaved: SetPostSavedUseCase
) : PostsViewModel(
    getPosts = getNextPosts::invoke,
    setPostSaved = setPostSaved::invoke
)