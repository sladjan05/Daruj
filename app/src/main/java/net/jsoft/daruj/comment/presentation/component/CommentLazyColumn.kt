package net.jsoft.daruj.comment.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.comment.domain.model.Comment
import net.jsoft.daruj.main.presentation.component.LoadingLazyColumn
import net.jsoft.daruj.main.presentation.component.LoadingLazyColumnState

@Composable
fun CommentLazyColumn(
    comments: List<Comment>,
    isLoading: Boolean,
    noContentText: String,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    swipeRefreshEnabled: Boolean = true,
    lazyListState: LazyListState = rememberLazyListState()
) {
    LoadingLazyColumn(
        lazyColumnState = when {
            isLoading && comments.isEmpty() -> LoadingLazyColumnState.LOADING
            comments.isEmpty() -> LoadingLazyColumnState.NO_CONTENT
            else -> LoadingLazyColumnState.LOADED
        },
        onRefresh = onRefresh,
        noContentText = noContentText,
        modifier = modifier,
        swipeRefreshEnabled = swipeRefreshEnabled,
        lazyListState = lazyListState
    ) {
        items(
            items = comments,
            key = { comment -> comment.data.id }
        ) { comment ->
            Comment(
                comment = comment,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (isLoading && comments.isNotEmpty()) item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )
            }
        }

        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}