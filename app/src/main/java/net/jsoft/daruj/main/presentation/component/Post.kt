package net.jsoft.daruj.main.presentation.component

import android.app.Activity
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import net.jsoft.daruj.R
import net.jsoft.daruj.comment.presentation.CommentsActivity
import net.jsoft.daruj.comment.presentation.viewmodel.CommentsViewModel
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.presentation.component.AnimatedClickableIcon
import net.jsoft.daruj.common.presentation.component.ProfilePicture
import net.jsoft.daruj.common.presentation.ui.theme.*
import net.jsoft.daruj.common.util.*
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.model.fullName
import net.jsoft.daruj.modify_post.presentation.ModifyPostActivity

val RobotoRegular14 = Roboto weight FontWeight.Normal size 14.sp
val RobotoLight18 = Roboto weight FontWeight.Light size 18.sp
val RobotoLight12 = Roboto weight FontWeight.Light size 12.sp

@Composable
fun Post(
    post: Post,
    canDonateBlood: Boolean,
    onExpand: () -> Unit,
    onReceiptsClick: () -> Unit,
    onDonateClick: () -> Unit,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    context as Activity

    val mutable = post.mutable
    val immutable = post.immutable
    val data = post.data

    Column(
        modifier = modifier
    ) {
        PostHeader(
            pictureUri = immutable.user.pictureUri,
            displayName = immutable.user.displayName,
            timestamp = run {
                val timestamp by formatTimestamp(immutable.timestamp).collectAsState(UiText.Empty)
                timestamp.value
            },
            isMyPost = data.isMyPost,
            receiptCount = data.receiptCount,
            onReceiptsClick = onReceiptsClick,
            onModifyClick = {
                val intention = ModifyPostActivity.Intention.EditPost(post)
                context.startActivity<ModifyPostActivity>(
                    ModifyPostActivity.Intention to intention
                )
            },
            onDeleteClick = onDeleteClick,
            modifier = Modifier.fillMaxWidth()
        )

        PostBody(
            pictureUri = data.pictureUri,
            recipientName = post.fullName,
            donorsRequired = mutable.donorsRequired,
            donorCount = immutable.donorCount,
            blood = mutable.blood,
            canDonateBlood = canDonateBlood,
            isBloodCompatible = data.isBloodCompatible,
            onDonateClick = onDonateClick,
            modifier = Modifier
                .fillMaxWidth()
                .indicationlessClickable(onExpand)
        )

        PostFooter(
            commentCount = immutable.commentCount,
            shareCount = immutable.shareCount,
            isSaved = data.isSaved,
            onCommentClick = {
                context.startActivity<CommentsActivity>(
                    CommentsViewModel.PostID to post.data.id
                )
            },
            onShareClick = onShareClick,
            onSaveClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PostHeader(
    pictureUri: Uri?,
    displayName: String,
    timestamp: String,
    isMyPost: Boolean,
    receiptCount: Int,
    onReceiptsClick: () -> Unit,
    onModifyClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bottomSheetDialogController = rememberBottomSheetDialogController()

    Row(
        modifier = modifier
            .height(60.dp)
            .padding(horizontal = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfilePicture(
                pictureUri = pictureUri,
                modifier = Modifier.size(40.dp)
            )

            Column {
                Text(
                    text = displayName,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = RobotoRegular14
                )

                Text(
                    text = timestamp,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = RobotoLight12
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (receiptCount != 0) {
                Box(
                    modifier = Modifier.size(30.dp)
                ) {
                    AnimatedClickableIcon(
                        painter = painterResource(R.drawable.ic_badge),
                        contentDescription = R.string.tx_donation_receipts.value,
                        onClick = onReceiptsClick,
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onBackground,
                        scaleTo = 0.6f
                    )

                    ReceiptCount(
                        count = receiptCount,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                }
            }

            if (isMyPost) {
                AnimatedClickableIcon(
                    painter = painterResource(R.drawable.ic_more),
                    contentDescription = R.string.tx_more.value,
                    onClick = bottomSheetDialogController::show,
                    modifier = Modifier.size(22.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                    scaleTo = 0.6f
                )
            }
        }
    }

    PostMoreBottomSheetDialog(
        controller = bottomSheetDialogController,
        onModifyClick = onModifyClick,
        onDeleteClick = onDeleteClick
    )
}

@Composable
fun PostBody(
    pictureUri: Uri?,
    recipientName: String,
    donorsRequired: Int,
    donorCount: Int,
    blood: Blood,
    canDonateBlood: Boolean,
    isBloodCompatible: Boolean,
    onDonateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .aspectRatio(1.6f)
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var isLoaded by rememberMutableStateOf(false)

        AsyncImage(
            model = pictureUri,
            contentDescription = recipientName,
            modifier = Modifier
                .weight(1f)
                .thenIf(
                    statement = !isLoaded,
                    ifModifier = Modifier.padding(35.dp),
                ),
            contentScale = if (isLoaded) ContentScale.Crop else ContentScale.Fit,
            error = painterResource(R.drawable.ic_full_logo),
            placeholder = painterResource(R.drawable.ic_full_logo),
            onSuccess = { isLoaded = true },
            onLoading = { isLoaded = false },
            colorFilter = isLoaded.ifFalse { ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface) }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(15.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = recipientName,
                    color = MaterialTheme.colorScheme.onSurfaceDim,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = R.string.tx_needs_blood.value,
                    color = MaterialTheme.colorScheme.onSurfaceDim,
                    style = RobotoLight18
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(R.string.tx_donor_count.value + ":  ")
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(donorCount.toString())
                        }
                    },
                    color = MaterialTheme.colorScheme.onSurfaceDim,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = buildAnnotatedString {
                        append(R.string.tx_donors_required.value + ":  ")
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(donorsRequired.toString())
                        }
                    },
                    color = MaterialTheme.colorScheme.onSurfaceDim,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = buildAnnotatedString {
                        append(R.string.tx_blood_type.value + ":  ")
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(blood.toString())
                        }
                    },
                    color = MaterialTheme.colorScheme.onSurfaceDim,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    progress = donorCount.toFloat() / donorsRequired,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.mShapes.full),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.onSurfaceLight
                )

                DonateButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onDonateClick,
                    enabled = canDonateBlood && isBloodCompatible && (donorCount < donorsRequired)
                )
            }
        }
    }
}

@Composable
fun PostFooter(
    commentCount: Int,
    shareCount: Int,
    isSaved: Boolean,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = 20.dp,
                vertical = 13.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedClickableIcon(
                    painter = painterResource(R.drawable.ic_comments),
                    contentDescription = R.string.tx_comments.value,
                    onClick = onCommentClick,
                    modifier = Modifier.size(22.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                    scaleTo = 0.6f
                )

                Text(
                    text = commentCount.toString(),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmaller
                )
            }

            /* TODO
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedClickableIcon(
                    painter = painterResource(R.drawable.ic_share),
                    contentDescription = R.string.tx_share.value,
                    onClick = onShareClick,
                    modifier = Modifier.size(22.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                    scaleTo = 0.6f
                )

                Text(
                    text = shareCount.toString(),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmaller
                )
            }
             */
        }

        AnimatedClickableIcon(
            painter = painterResource(if (isSaved) R.drawable.ic_saved_filled else R.drawable.ic_saved),
            contentDescription = if (isSaved) R.string.tx_unsave.value else R.string.tx_save.value,
            onClick = onSaveClick,
            modifier = Modifier.size(22.dp),
            tint = if (isSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
        )
    }
}