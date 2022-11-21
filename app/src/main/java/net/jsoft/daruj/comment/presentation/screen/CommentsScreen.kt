package net.jsoft.daruj.comment.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.comment.presentation.component.CommentLazyColumn
import net.jsoft.daruj.comment.presentation.viewmodel.CommentsEvent
import net.jsoft.daruj.comment.presentation.viewmodel.CommentsTask
import net.jsoft.daruj.comment.presentation.viewmodel.CommentsViewModel
import net.jsoft.daruj.common.presentation.component.AnimatedClickableIcon
import net.jsoft.daruj.common.presentation.component.TextBox
import net.jsoft.daruj.common.util.goBack
import net.jsoft.daruj.common.util.showShortToast
import net.jsoft.daruj.common.util.value

@Composable
fun CommentsScreen(
    viewModel: CommentsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is CommentsTask.ShowError -> context.showShortToast(task.message.getValue(context))
            }
        }
    }

    BackHandler { context.goBack() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CommentLazyColumn(
            comments = viewModel.comments,
            isLoading = viewModel.isLoading,
            noContentText = R.string.tx_no_comments.value,
            onRefresh = { viewModel.onEvent(CommentsEvent.Refresh) },
            modifier = Modifier.fillMaxSize()
        )

        TextBox(
            text = viewModel.comment.value,
            onValueChange = { comment -> viewModel.onEvent(CommentsEvent.CommentChange(comment)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.BottomCenter),
            hint = R.string.tx_enter_comment.value,
            trailingIcon = {
                AnimatedClickableIcon(
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = R.string.tx_send.value,
                    onClick = { viewModel.onEvent(CommentsEvent.PostCommentClick) },
                    enabled = !viewModel.isLoading && viewModel.comment.value.isNotBlank(),
                    modifier = Modifier
                        .size(25.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    scaleTo = 0.6f
                )
            }
        )
    }
}