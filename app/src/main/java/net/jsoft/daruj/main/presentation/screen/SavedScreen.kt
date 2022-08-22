package net.jsoft.daruj.main.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.asUiText
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.utils.value
import net.jsoft.daruj.main.presentation.component.LoadingLazyColumn
import net.jsoft.daruj.main.presentation.component.LoadingLazyColumnState
import net.jsoft.daruj.main.presentation.component.Post
import net.jsoft.daruj.main.presentation.screen.viewmodel.saved.SavedEvent
import net.jsoft.daruj.main.presentation.screen.viewmodel.saved.SavedViewModel

@Composable
fun SavedScreen(
    navController: NavController,
    viewModel: SavedViewModel = hiltViewModel()
) {
    LoadingLazyColumn(
        lazyColumnState = when {
            viewModel.isLoading && viewModel.posts.isEmpty() -> LoadingLazyColumnState.LOADING
            viewModel.posts.isEmpty() && !viewModel.isLoading -> LoadingLazyColumnState.NO_CONTENT
            else -> LoadingLazyColumnState.LOADED
        },
        onRefresh = {
            viewModel.onEvent(SavedEvent.Refresh)
        },
        noContentText = R.string.tx_no_saved_posts.value,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = MaterialTheme.spacing.small)
    ) {
        items(count = viewModel.posts.size) { index ->
            val post = viewModel.posts[index]

            Post(
                post = post,
                modifier = Modifier.fillMaxWidth(),
                onExpand = {
                    val route = Screen.DetailedPost.route(
                        Screen.DetailedPost.POST_ID to post.data.id
                    )

                    navController.navigate(route)
                },
                onDonateClick = {

                },
                onCommentClick = {

                },
                onShareClick = {

                },
                onSaveClick = {
                    viewModel.onEvent(SavedEvent.SaveClick(post))
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}