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
import net.jsoft.daruj.main.domain.model.Donation
import java.lang.Integer.max

@Composable
fun DonationLazyColumn(
    donations: List<Donation>,
    isLoading: Boolean,
    noContentText: String,
    onRefresh: () -> Unit,
    onEndReached: () -> Unit,
    onPostClick: (donation: Donation) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val firstIndex by derivedStateOf { lazyListState.firstVisibleItemIndex }

    LaunchedEffect(firstIndex) {
        if (donations.isEmpty() || (firstIndex > max(0, donations.size - 5))) onEndReached()
    }

    LoadingLazyColumn(
        lazyColumnState = when {
            isLoading && donations.isEmpty() -> LoadingLazyColumnState.LOADING
            donations.isEmpty() -> LoadingLazyColumnState.NO_CONTENT
            else -> LoadingLazyColumnState.LOADED
        },
        onRefresh = onRefresh,
        noContentText = noContentText,
        modifier = modifier,
        lazyListState = lazyListState
    ) {
        items(
            items = donations,
            key = { donation -> donation.timestamp }
        ) { donation ->
            Donation(
                donation = donation,
                onPostClick = { onPostClick(donation) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (isLoading && donations.isNotEmpty()) item {
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