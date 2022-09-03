package net.jsoft.daruj.main.presentation.screen

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.component.AnimatedClickableIcon
import net.jsoft.daruj.common.presentation.component.ProfilePicture
import net.jsoft.daruj.common.presentation.ui.theme.onBackgroundDim
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.utils.NoOverscrollEffect
import net.jsoft.daruj.common.utils.showShortToast
import net.jsoft.daruj.common.utils.value
import net.jsoft.daruj.main.presentation.component.PageNavigation
import net.jsoft.daruj.main.presentation.component.Points
import net.jsoft.daruj.main.presentation.screen.viewmodel.profile.ProfileEvent
import net.jsoft.daruj.main.presentation.screen.viewmodel.profile.ProfileTask
import net.jsoft.daruj.main.presentation.screen.viewmodel.profile.ProfileViewModel

@Composable
@OptIn(ExperimentalPagerApi::class)
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    val scope = rememberCoroutineScope()
    var pageChangeJob: Job? = null

    val pagerState = rememberPagerState(initialPage = viewModel.page)
    val currentPage by derivedStateOf { pagerState.currentPage }

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is ProfileTask.ShowError -> context.showShortToast(task.message.getValue(context))
            }
        }
    }

    LaunchedEffect(currentPage) {
        viewModel.onEvent(ProfileEvent.PageChange(currentPage))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = MaterialTheme.spacing.small,
                    horizontal = MaterialTheme.spacing.large
                )
        ) {
            if (viewModel.localUser != null) {
                Text(
                    text = viewModel.localUser!!.immutable.displayName,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            AnimatedClickableIcon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = R.string.tx_settings.value,
                onClick = {
                    TODO()
                },
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterEnd),
                tint = MaterialTheme.colorScheme.onBackgroundDim,
                scaleTo = 0.6f
            )
        }

        Box(
            modifier = Modifier.height(145.dp)
        ) {
            ProfilePicture(
                pictureUri = viewModel.localUser?.data?.pictureUri,
                modifier = Modifier.size(120.dp)
            )

            Points(
                points = viewModel.localUser?.immutable?.points ?: 0,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        PageNavigation(
            R.string.tx_my_posts.value,
            R.string.tx_donations.value,
            currentPage = viewModel.page,
            onPageChange = { page ->
                pageChangeJob?.cancel()
                pageChangeJob = scope.launch {
                    pagerState.animateScrollToPage(page)
                }

                viewModel.onEvent(ProfileEvent.PageChange(page))
            },
            modifier = Modifier.fillMaxWidth(0.5f)
        )

        NoOverscrollEffect {
            HorizontalPager(
                count = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> MyPostsScreen(navController)
                }
            }
        }
    }
}