package net.jsoft.daruj.main.presentation.component

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
import net.jsoft.daruj.main.domain.model.Receipt

@Composable
fun ReceiptLazyColumn(
    receipts: List<Receipt>,
    isLoading: Boolean,
    noContentText: String,
    onRefresh: () -> Unit,
    onApproveClick: (receipt: Receipt) -> Unit,
    onDenyClick: (receipt: Receipt) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    LoadingLazyColumn(
        lazyColumnState = when {
            isLoading && receipts.isEmpty() -> LoadingLazyColumnState.LOADING
            receipts.isEmpty() -> LoadingLazyColumnState.NO_CONTENT
            else -> LoadingLazyColumnState.LOADED
        },
        onRefresh = onRefresh,
        noContentText = noContentText,
        modifier = modifier,
        lazyListState = lazyListState
    ) {
        items(
            items = receipts,
            key = { receipt -> receipt.user.id }
        ) { receipt ->
            Receipt(
                receipt = receipt,
                onApproveClick = { onApproveClick(receipt) },
                onDenyClick = { onDenyClick(receipt) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
        }

        if (isLoading && receipts.isNotEmpty()) item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 10.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )
            }
        }

        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}