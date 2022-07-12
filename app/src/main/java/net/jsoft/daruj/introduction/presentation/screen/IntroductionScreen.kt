package net.jsoft.daruj.introduction.presentation.screen

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.AuthActivity
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.common.util.value
import net.jsoft.daruj.introduction.presentation.component.IntroductionIllustration
import net.jsoft.daruj.introduction.presentation.component.PageIndicator
import net.jsoft.daruj.introduction.presentation.viewmodel.IntroductionEvent
import net.jsoft.daruj.introduction.presentation.viewmodel.IntroductionTask
import net.jsoft.daruj.introduction.presentation.viewmodel.IntroductionViewModel

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun IntroductionScreen(
    viewModel: IntroductionViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val pagerState = remember { PagerState() }
    var page by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }.collectLatest { newPage ->
            viewModel.onEvent(IntroductionEvent.PageSwitch(newPage))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.page.collectLatest { newPage ->
            page = newPage
            pagerState.animateScrollToPage(page)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when(task) {
                is IntroductionTask.Next -> {
                    val intent = Intent(
                        context,
                        AuthActivity::class.java
                    )

                    context.startActivity(intent)
                    (context as ComponentActivity).finish()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
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
                state = pagerState
            ) { page ->
                IntroductionIllustration(
                    painter = painterResource(illustrations[page]),
                    title = titles[page].value,
                    description = descriptions[page].value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-20).dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-32).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PageIndicator(
                pageCount = IntroductionViewModel.PAGE_COUNT,
                page = page,
                modifier = Modifier.width(125.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            PrimaryButton(
                text = if (page == IntroductionViewModel.PAGE_COUNT - 1) {
                    R.string.tx_finish.value
                } else {
                    R.string.tx_next.value
                },
                modifier = Modifier.widthIn(300.dp),
                onClick = {
                    viewModel.onEvent(IntroductionEvent.Next)
                }
            )
        }
    }
}