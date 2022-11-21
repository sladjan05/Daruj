package net.jsoft.daruj.introduction.presentation.screen

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.AuthActivity
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.util.NoOverscrollEffect
import net.jsoft.daruj.common.util.switchActivity
import net.jsoft.daruj.common.util.value
import net.jsoft.daruj.introduction.presentation.component.IntroductionIllustration
import net.jsoft.daruj.introduction.presentation.component.PageIndicator
import net.jsoft.daruj.introduction.presentation.viewmodel.IntroductionEvent
import net.jsoft.daruj.introduction.presentation.viewmodel.IntroductionViewModel

@Composable
@OptIn(ExperimentalPagerApi::class)
fun IntroductionScreen(
    viewModel: IntroductionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    val scope = rememberCoroutineScope()
    var pageChangeJob: Job? = null

    val pagerState = rememberPagerState(initialPage = viewModel.page)
    val currentPage by derivedStateOf { pagerState.currentPage }

    LaunchedEffect(currentPage) { viewModel.onEvent(IntroductionEvent.PageChange(currentPage)) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val illustrations = arrayOf(
            R.drawable.ic_tutorial_illustration_1,
            R.drawable.ic_tutorial_illustration_2,
            R.drawable.ic_tutorial_illustration_3
        )

        val titles = arrayOf(
            R.string.tx_tutorial_illustration_title_1,
            R.string.tx_tutorial_illustration_title_2,
            R.string.tx_tutorial_illustration_title_3
        )

        val descriptions = arrayOf(
            R.string.tx_tutorial_illustration_description_1,
            R.string.tx_tutorial_illustration_description_2,
            R.string.tx_tutorial_illustration_description_3
        )

        NoOverscrollEffect {
            HorizontalPager(
                count = IntroductionViewModel.PageCount,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically
            ) { page ->
                IntroductionIllustration(
                    painter = painterResource(illustrations[page]),
                    title = titles[page].value,
                    description = descriptions[page].value,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Column(
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            PageIndicator(
                pageCount = IntroductionViewModel.PageCount,
                page = viewModel.page,
                modifier = Modifier.width(125.dp)
            )

            val isLastPage = viewModel.page == (IntroductionViewModel.PageCount - 1)

            PrimaryButton(
                text = if (isLastPage) R.string.tx_finish.value else R.string.tx_next.value,
                onClick = {
                    if (isLastPage) context.switchActivity<AuthActivity>()
                    else {
                        val page = viewModel.page + 1

                        pageChangeJob?.cancel()
                        pageChangeJob = scope.launch {
                            pagerState.animateScrollToPage(page)
                        }

                        viewModel.onEvent(IntroductionEvent.PageChange(page))
                    }
                },
                modifier = Modifier.width(300.dp)
            )
        }
    }
}