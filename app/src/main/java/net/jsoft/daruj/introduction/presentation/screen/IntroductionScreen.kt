package net.jsoft.daruj.introduction.presentation.screen

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.AuthActivity
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.utils.switchActivity
import net.jsoft.daruj.common.utils.value
import net.jsoft.daruj.introduction.presentation.component.IntroductionIllustration
import net.jsoft.daruj.introduction.presentation.component.PageIndicator
import net.jsoft.daruj.introduction.presentation.viewmodel.IntroductionEvent
import net.jsoft.daruj.introduction.presentation.viewmodel.IntroductionTask
import net.jsoft.daruj.introduction.presentation.viewmodel.IntroductionViewModel

@Composable
@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
fun IntroductionScreen(
    viewModel: IntroductionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    val pagerState = rememberPagerState()

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }.collectLatest { newPage ->
            viewModel.onEvent(IntroductionEvent.PageSwitch(newPage))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collect { task ->
            when (task) {
                is IntroductionTask.SwitchPage -> pagerState.animateScrollToPage(viewModel.page)
                is IntroductionTask.Finish -> context.switchActivity<AuthActivity>()
            }
        }
    }

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

        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            HorizontalPager(
                count = IntroductionViewModel.PAGE_COUNT,
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
                pageCount = IntroductionViewModel.PAGE_COUNT,
                page = viewModel.page,
                modifier = Modifier.width(125.dp)
            )

            PrimaryButton(
                text = if (viewModel.page == IntroductionViewModel.PAGE_COUNT - 1) {
                    R.string.tx_finish.value
                } else {
                    R.string.tx_next.value
                },
                onClick = {
                    viewModel.onEvent(IntroductionEvent.Next)
                },
                modifier = Modifier.width(300.dp)
            )
        }
    }
}