package net.jsoft.daruj.main.presentation.component

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import net.jsoft.daruj.common.util.NoOverscrollEffect

enum class LoadingLazyColumnState {
    LOADED,
    LOADING,
    NO_CONTENT
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoadingLazyColumn(
    lazyColumnState: LoadingLazyColumnState,
    noContentText: String,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    swipeRefreshEnabled: Boolean = true,
    content: LazyListScope.() -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(lazyColumnState == LoadingLazyColumnState.LOADING),
        onRefresh = onRefresh,
        modifier = modifier,
        swipeEnabled = swipeRefreshEnabled,
        refreshTriggerDistance = 60.dp,
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            NoOverscrollEffect {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    lazyListState,
                    contentPadding,
                    reverseLayout,
                    verticalArrangement,
                    horizontalAlignment,
                    flingBehavior,
                    userScrollEnabled,
                    content
                )
            }

            if (lazyColumnState == LoadingLazyColumnState.NO_CONTENT) {
                Text(
                    text = noContentText,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
    }
}