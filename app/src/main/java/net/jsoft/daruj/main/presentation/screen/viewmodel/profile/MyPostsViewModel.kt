package net.jsoft.daruj.main.presentation.screen.viewmodel.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.main.domain.usecase.GetMyPostsUseCase
import net.jsoft.daruj.main.domain.usecase.SetPostSavedUseCase
import net.jsoft.daruj.main.presentation.screen.viewmodel.posts.PostsViewModel
import javax.inject.Inject

@HiltViewModel
class MyPostsViewModel @Inject constructor(
    getMyPosts: GetMyPostsUseCase,
    setPostSaved: SetPostSavedUseCase
) : PostsViewModel(
    getPosts = getMyPosts::invoke,
    setPostSaved = setPostSaved::invoke
)