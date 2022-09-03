package net.jsoft.daruj.main.presentation.screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.component.AnimatedClickableIcon
import net.jsoft.daruj.common.presentation.ui.theme.onBackgroundDim
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.utils.showShortToast
import net.jsoft.daruj.common.utils.startActivity
import net.jsoft.daruj.common.utils.value
import net.jsoft.daruj.main.presentation.component.PostLazyColumn
import net.jsoft.daruj.main.presentation.screen.viewmodel.home.HomeViewModel
import net.jsoft.daruj.main.presentation.screen.viewmodel.posts.PostsEvent
import net.jsoft.daruj.main.presentation.screen.viewmodel.posts.PostsTask
import net.jsoft.daruj.modify_post.presentation.ModifyPostActivity

class HomeScreenController {
    var scrollToTop by mutableStateOf(false)
        private set

    suspend fun scrollToTop() {
        scrollToTop = true
        delay(1000L)
        scrollToTop = false
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    controller: HomeScreenController = remember { HomeScreenController() }
) {
    val context = LocalContext.current
    context as Activity

    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is PostsTask.ShowError -> context.showShortToast(task.message.getValue(context))
            }
        }
    }

    LaunchedEffect(controller.scrollToTop) {
        if (controller.scrollToTop) {
            lazyListState.animateScrollToItem(0)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_full_logo),
                contentDescription = null,
                modifier = Modifier.height(50.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                AnimatedClickableIcon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = R.string.tx_create_new_post.value,
                    onClick = {
                        context.startActivity<ModifyPostActivity>(
                            ModifyPostActivity.PURPOSE to ModifyPostActivity.Purpose.CreatePost
                        )
                    },
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.onBackgroundDim,
                    scaleTo = 0.6f
                )
            }
        }

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
            modifier = Modifier.fillMaxSize(),
            lazyListState = lazyListState
        )
    }
}