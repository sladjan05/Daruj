package net.jsoft.daruj.main.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.common.util.showShortToast
import net.jsoft.daruj.common.util.value
import net.jsoft.daruj.main.presentation.component.ReceiptLazyColumn
import net.jsoft.daruj.main.presentation.viewmodel.receipt.ReceiptsEvent
import net.jsoft.daruj.main.presentation.viewmodel.receipt.ReceiptsTask
import net.jsoft.daruj.main.presentation.viewmodel.receipt.ReceiptsViewModel

@Composable
fun ReceiptsScreen(
    viewModel: ReceiptsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is ReceiptsTask.ShowError -> context.showShortToast(task.message.getValue(context))
                is ReceiptsTask.ShowInfo -> context.showShortToast(task.message.getValue(context))
            }
        }
    }

    ReceiptLazyColumn(
        receipts = viewModel.receipts,
        isLoading = viewModel.isLoading,
        noContentText = R.string.tx_no_receipts.value,
        onRefresh = { viewModel.onEvent(ReceiptsEvent.Refresh) },
        onApproveClick = { receipt -> viewModel.onEvent(ReceiptsEvent.ApproveClick(receipt)) },
        onDenyClick = { receipt -> viewModel.onEvent(ReceiptsEvent.DenyClick(receipt)) },
        modifier = Modifier.fillMaxSize()
    )
}