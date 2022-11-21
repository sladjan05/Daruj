package net.jsoft.daruj.main.presentation.screen

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.util.*
import net.jsoft.daruj.donate_blood.presentation.DonateBloodActivity
import net.jsoft.daruj.main.presentation.component.PageNavigation
import net.jsoft.daruj.main.presentation.component.PostLazyColumn
import net.jsoft.daruj.main.presentation.component.SearchBar
import net.jsoft.daruj.main.presentation.viewmodel.posts.PostsEvent
import net.jsoft.daruj.main.presentation.viewmodel.posts.PostsTask
import net.jsoft.daruj.main.presentation.viewmodel.search.SearchEvent
import net.jsoft.daruj.main.presentation.viewmodel.search.SearchViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    val pagerState = rememberPagerState()

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is PostsTask.ShowError -> context.showShortToast(task.message.getValue(context))
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            text = viewModel.criteria.value,
            onValueChange = { search -> viewModel.onEvent(SearchEvent.SearchChange(search)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium
                ),
            hint = R.string.tx_enter_exact_name_and_surname.value
        )

        PageNavigation(
            "Objave",
            "Profili",
            currentPage = 0,// TODO: Profiles
            onPageChange = { context.showShortToast(R.string.tx_soon.getValue(context)) },
            modifier = Modifier.fillMaxWidth(0.5f)
        )

        NoOverscrollEffect {
            HorizontalPager(
                count = 2,
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> PostLazyColumn(
                        posts = viewModel.posts,
                        isLoading = viewModel.isLoading,
                        noContentText = R.string.tx_no_results.value,
                        canDonateBlood = viewModel.canDonateBlood,
                        onRefresh = { },
                        onEndReached = { },
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
                        onShareClick = { },
                        onSaveClick = { post -> viewModel.onEvent(PostsEvent.SaveClick(post)) },
                        onDeleteClick = { post -> viewModel.onEvent(PostsEvent.DeleteClick(post)) },
                        modifier = Modifier.fillMaxWidth(),
                        swipeRefreshEnabled = false
                    )
                }
            }
        }
    }
}