package net.jsoft.daruj.main.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import net.jsoft.daruj.R
import net.jsoft.daruj.common.utils.value
import net.jsoft.daruj.main.presentation.component.PostLazyColumn
import net.jsoft.daruj.main.presentation.screen.viewmodel.posts.PostsEvent
import net.jsoft.daruj.main.presentation.screen.viewmodel.profile.MyPostsViewModel

@Composable
fun MyPostsScreen(
    navController: NavController,
    viewModel: MyPostsViewModel = hiltViewModel()
) {
    PostLazyColumn(
        posts = viewModel.posts,
        isLoading = viewModel.isLoading,
        noContentText = R.string.tx_no_posts.value,
        onRefresh = {
            viewModel.onEvent(PostsEvent.Refresh)
        },
        onEndReached = {
            viewModel.onEvent(PostsEvent.ReachedEnd)
        },
        onExpand = { post ->
            val route = Screen.DetailedPost.route(
                Screen.DetailedPost.POST_ID to post.data.id
            )

            navController.navigate(route)
        },
        onDonateClick = {},
        onCommentClick = {},
        onShareClick = {},
        onSaveClick = { post ->
            viewModel.onEvent(PostsEvent.SaveClick(post))
        },
        modifier = Modifier.fillMaxSize()
    )
}