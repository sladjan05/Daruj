package net.jsoft.daruj.main.presentation.screen

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import net.jsoft.daruj.R
import net.jsoft.daruj.common.util.startActivity
import net.jsoft.daruj.common.util.value
import net.jsoft.daruj.donate_blood.presentation.DonateBloodActivity
import net.jsoft.daruj.main.presentation.component.PostLazyColumn
import net.jsoft.daruj.main.presentation.viewmodel.posts.PostsEvent
import net.jsoft.daruj.main.presentation.viewmodel.saved.SavedViewModel

@Composable
fun SavedScreen(
    navController: NavController,
    viewModel: SavedViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    PostLazyColumn(
        posts = viewModel.posts,
        isLoading = viewModel.isLoading,
        noContentText = R.string.tx_no_saved_posts.value,
        canDonateBlood = viewModel.canDonateBlood,
        onRefresh = { viewModel.onEvent(PostsEvent.Refresh) },
        onEndReached = { viewModel.onEvent(PostsEvent.ReachedEnd) },
        onExpand = { post ->
            val route = Screen.DetailedPost.route(
                Screen.DetailedPost.POST_ID to post.data.id
            )

            navController.navigate(route)
        },
        onReceiptsClick = { post ->
            val route = Screen.Receipts.route(
                Screen.Receipts.POST_ID to post.data.id
            )

            navController.navigate(route)
        },
        onDonateClick = { post ->
            context.startActivity<DonateBloodActivity>(
                DonateBloodActivity.Post to post
            )
        },
        onShareClick = { TODO() },
        onSaveClick = { post -> viewModel.onEvent(PostsEvent.SaveClick(post)) },
        onDeleteClick = { post -> viewModel.onEvent(PostsEvent.DeleteClick(post)) },
        modifier = Modifier.fillMaxSize()
    )
}