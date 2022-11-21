package net.jsoft.daruj.main.presentation.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.comment.presentation.CommentsActivity
import net.jsoft.daruj.comment.presentation.viewmodel.CommentsViewModel
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.presentation.ui.theme.*
import net.jsoft.daruj.common.util.*
import net.jsoft.daruj.donate_blood.presentation.DonateBloodActivity
import net.jsoft.daruj.main.presentation.component.DonateButton
import net.jsoft.daruj.main.presentation.component.PostFooter
import net.jsoft.daruj.main.presentation.component.PostHeader
import net.jsoft.daruj.main.presentation.component.RobotoLight18
import net.jsoft.daruj.main.presentation.viewmodel.detailed_post.DetailedPostEvent
import net.jsoft.daruj.main.presentation.viewmodel.detailed_post.DetailedPostTask
import net.jsoft.daruj.main.presentation.viewmodel.detailed_post.DetailedPostViewModel
import net.jsoft.daruj.modify_post.presentation.ModifyPostActivity

@Composable
fun DetailedPostScreen(
    navController: NavController,
    viewModel: DetailedPostViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is DetailedPostTask.Finish -> navController.popBackStack()

                is DetailedPostTask.ShowError -> context.showShortToast(task.message.getValue(context))
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val post = viewModel.post

        val mutable = post?.mutable
        val immutable = post?.immutable
        val data = post?.data

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            PostHeader(
                pictureUri = immutable?.user?.pictureUri,
                displayName = immutable?.user?.displayName ?: "",
                timestamp = run {
                    if (immutable?.timestamp == null) return@run ""

                    val timestamp by formatTimestamp(immutable.timestamp).collectAsState(UiText.Empty)
                    timestamp.value
                },
                isMyPost = data?.isMyPost ?: false,
                receiptCount = data?.receiptCount ?: 0,
                onReceiptsClick = {
                    val route = Screen.Receipts.route(
                        Screen.Receipts.POST_ID to data!!.id
                    )

                    navController.navigate(route)
                },
                onModifyClick = {
                    if (post != null) {
                        val intention = ModifyPostActivity.Intention.EditPost(post)
                        context.startActivity<ModifyPostActivity>(
                            ModifyPostActivity.Intention to intention
                        )
                    }
                },
                onDeleteClick = { viewModel.onEvent(DetailedPostEvent.DeleteClick) },
                modifier = Modifier.fillMaxWidth()
            )

            var isLoaded by rememberMutableStateOf(false)

            AsyncImage(
                model = data?.pictureUri,
                contentDescription = mutable?.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .thenIf(
                        statement = !isLoaded,
                        ifModifier = Modifier.padding(120.dp),
                    ),
                contentScale = if (isLoaded) ContentScale.Crop else ContentScale.Fit,
                error = painterResource(R.drawable.ic_full_logo),
                placeholder = painterResource(R.drawable.ic_full_logo),
                onSuccess = { isLoaded = true },
                onLoading = { isLoaded = false },
                colorFilter = isLoaded.ifFalse { ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface) }
            )

            PostFooter(
                commentCount = immutable?.commentCount ?: 0,
                shareCount = immutable?.shareCount ?: 0,
                isSaved = data?.isSaved ?: false,
                onCommentClick = {
                    if (post?.data != null) {
                        context.startActivity<CommentsActivity>(
                            CommentsViewModel.PostID to post.data.id
                        )
                    }
                },
                onShareClick = { TODO() },
                onSaveClick = { viewModel.onEvent(DetailedPostEvent.SaveClick) },
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = MaterialTheme.spacing.huge * 2,
                        start = MaterialTheme.spacing.large,
                        end = MaterialTheme.spacing.large
                    ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp
                    )
                }

                if (mutable != null && immutable != null) {
                    Row {
                        Text(
                            text = "${mutable.name} ${mutable.surname} ",
                            modifier = Modifier.alignByBaseline(),
                            color = MaterialTheme.colorScheme.onSurfaceDim,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = R.string.tx_needs_blood.value,
                            modifier = Modifier.alignByBaseline(),
                            color = MaterialTheme.colorScheme.onSurfaceDim,
                            style = RobotoLight18
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.mShapes.medium
                            )
                            .padding(15.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append(R.string.tx_donor_count.value + ":  ")
                                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append(immutable.donorCount.toString())
                                }
                            },
                            color = MaterialTheme.colorScheme.onSurfaceDim,
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            text = buildAnnotatedString {
                                append(R.string.tx_donors_required.value + ":  ")
                                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append(mutable.donorsRequired.toString())
                                }
                            },
                            color = MaterialTheme.colorScheme.onSurfaceDim,
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            text = buildAnnotatedString {
                                append(R.string.tx_blood_type.value + ":  ")
                                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append(mutable.blood.toString())
                                }
                            },
                            color = MaterialTheme.colorScheme.onSurfaceDim,
                            style = MaterialTheme.typography.bodySmall
                        )

                        LinearProgressIndicator(
                            progress = immutable.donorCount.toFloat() / mutable.donorsRequired,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                                .clip(MaterialTheme.mShapes.full),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.onSurfaceLight
                        )
                    }

                    Text(
                        text = mutable.description,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackgroundDim,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        if (post != null) {
            val donorCount = immutable?.donorCount ?: 0
            val donorsRequired = mutable?.donorsRequired ?: 0
            val isFull = donorCount < donorsRequired

            DonateButton(
                onClick = {
                    context.startActivity<DonateBloodActivity>(
                        DonateBloodActivity.Post to post
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.spacing.large,
                        end = MaterialTheme.spacing.large,
                        bottom = MaterialTheme.spacing.huge
                    )
                    .align(Alignment.BottomCenter),
                enabled = viewModel.canDonateBlood && post.data.isBloodCompatible && isFull
            )
        }
    }
}