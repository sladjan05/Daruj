package net.jsoft.daruj.main.presentation.screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.asUiText
import net.jsoft.daruj.common.presentation.component.AnimatedClickableIcon
import net.jsoft.daruj.common.presentation.ui.theme.onBackgroundDim
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.utils.startActivity
import net.jsoft.daruj.common.utils.value
import net.jsoft.daruj.main.presentation.component.LoadingLazyColumn
import net.jsoft.daruj.main.presentation.component.LoadingLazyColumnState
import net.jsoft.daruj.main.presentation.component.Post
import net.jsoft.daruj.main.presentation.screen.viewmodel.home.HomeEvent
import net.jsoft.daruj.main.presentation.screen.viewmodel.home.HomeTask
import net.jsoft.daruj.main.presentation.screen.viewmodel.home.HomeViewModel
import net.jsoft.daruj.modify_post.presentation.ModifyPostActivity

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collect { task ->
            when (task) {
                is HomeTask.ShowError -> Toast.makeText(
                    context,
                    task.uiText.getValue(context),
                    Toast.LENGTH_SHORT
                ).show()
            }
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

        LoadingLazyColumn(
            lazyColumnState = when {
                viewModel.isLoading && viewModel.posts.isEmpty() -> LoadingLazyColumnState.LOADING
                viewModel.posts.isEmpty() && !viewModel.isLoading -> LoadingLazyColumnState.NO_CONTENT
                else -> LoadingLazyColumnState.LOADED
            },
            onRefresh = {
                viewModel.onEvent(HomeEvent.Refresh)
            },
            noContentText = R.string.tx_no_posts.value,
            modifier = Modifier.fillMaxSize()
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
                        viewModel.onEvent(HomeEvent.SaveClick(post))
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}