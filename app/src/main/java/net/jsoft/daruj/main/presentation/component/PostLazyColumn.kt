package net.jsoft.daruj.main.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.main.domain.model.Post

@Composable
fun PostLazyColumn(
    posts: List<Post>,
    isLoading: Boolean,
    noContentText: String,
    onRefresh: () -> Unit,
    onEndReached: () -> Unit,
    onExpand: (post: Post) -> Unit,
    onDonateClick: (post: Post) -> Unit,
    onCommentClick: (post: Post) -> Unit,
    onShareClick: (post: Post) -> Unit,
    onSaveClick: (post: Post) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val firstIndex by derivedStateOf { lazyListState.firstVisibleItemIndex }

    LaunchedEffect(firstIndex) {
        if (firstIndex > posts.size - 5) onEndReached()
    }

    LoadingLazyColumn(
        lazyColumnState = when {
            isLoading && posts.isEmpty() -> LoadingLazyColumnState.LOADING
            posts.isEmpty() && !isLoading -> LoadingLazyColumnState.NO_CONTENT
            else -> LoadingLazyColumnState.LOADED
        },
        onRefresh = onRefresh,
        noContentText = noContentText,
        modifier = modifier,
        lazyListState = lazyListState
    ) {
        items(
            items = posts,
            key = { post -> post.data.id }
        ) { post ->
            Post(
                post = post,
                modifier = Modifier.fillMaxWidth(),
                onExpand = {
                    onExpand(post)
                },
                onDonateClick = {
                    onDonateClick(post)
                },
                onCommentClick = {
                    onCommentClick(post)
                },
                onShareClick = {
                    onShareClick(post)
                },
                onSaveClick = {
                    onSaveClick(post)
                }
            )
        }

        if (isLoading && posts.isNotEmpty()) item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}