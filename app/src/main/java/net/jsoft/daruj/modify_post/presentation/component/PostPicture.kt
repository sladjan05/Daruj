package net.jsoft.daruj.modify_post.presentation.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.UCropActivityResultContract
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.utils.clickableIf
import net.jsoft.daruj.common.utils.thenIf
import net.jsoft.daruj.common.utils.value

@Composable
fun PostPicture(
    pictureUri: Uri?,
    onPictureChange: (uri: Uri) -> Unit,
    modifier: Modifier = Modifier,
    canChangePicture: Boolean = true
) {
    val cropImageLauncher = rememberLauncherForActivityResult(
        contract = UCropActivityResultContract()
    ) { uri ->
        if (uri != null) onPictureChange(uri)
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) cropImageLauncher.launch(uri)
    }

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.mShapes.medium
            )
            .clickableIf(
                clickable = canChangePicture,
                shape = MaterialTheme.mShapes.medium
            ) {
                pickImageLauncher.launch("image/*")
            }
    ) {
        var isLoaded by rememberSaveable {
            mutableStateOf(false)
        }

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
            contentScale = if (isLoaded) {
                ContentScale.Crop
            } else {
                ContentScale.Fit
            },
            error = painterResource(R.drawable.ic_picture),
            placeholder = painterResource(R.drawable.ic_picture),
            onSuccess = {
                isLoaded = true
            },
            colorFilter = if (isLoaded) {
                null
            } else {
                ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface)
            }
        )
    }
}