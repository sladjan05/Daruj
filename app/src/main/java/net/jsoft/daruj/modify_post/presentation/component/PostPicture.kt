package net.jsoft.daruj.modify_post.presentation.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.component.PictureBottomSheetDialog
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.util.*

@Composable
fun PostPicture(
    pictureUri: Uri?,
    onPictureChange: (uri: Uri) -> Unit,
    modifier: Modifier = Modifier,
    canChangePicture: Boolean = true
) {
    val bottomSheetDialogController = rememberBottomSheetDialogController()

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.mShapes.medium
            )
            .clickableIf(
                clickable = canChangePicture,
                shape = MaterialTheme.mShapes.medium,
                onClick = bottomSheetDialogController::show
            )
    ) {
        var isLoaded by rememberMutableStateOf(false)

        AsyncImage(
            model = pictureUri,
            contentDescription = R.string.tx_post_picture.value,
            modifier = Modifier
                .fillMaxWidth()
                .thenIf(
                    statement = !isLoaded,
                    ifModifier = Modifier.fillMaxHeight(0.4f)
                )
                .clip(MaterialTheme.mShapes.medium)
                .align(Alignment.Center),
            contentScale = if (isLoaded) ContentScale.Crop else ContentScale.Fit,
            error = painterResource(R.drawable.ic_picture),
            placeholder = painterResource(R.drawable.ic_picture),
            onSuccess = { isLoaded = true },
            onLoading = { isLoaded = false },
            colorFilter = isLoaded.ifFalse { ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface) }
        )
    }

    PictureBottomSheetDialog(
        controller = bottomSheetDialogController,
        onPictureChange = onPictureChange
    )
}