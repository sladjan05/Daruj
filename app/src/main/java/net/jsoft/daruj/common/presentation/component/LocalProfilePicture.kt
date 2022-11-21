package net.jsoft.daruj.common.presentation.component

import android.app.Activity
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.ui.theme.full
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.util.clickable
import net.jsoft.daruj.common.util.rememberBottomSheetDialogController
import net.jsoft.daruj.common.util.value

@Composable
fun LocalProfilePicture(
    pictureUri: Uri?,
    onPictureChange: (uri: Uri) -> Unit,
    modifier: Modifier = Modifier,
    pictureChangeEnabled: Boolean = true
) {
    val context = LocalContext.current
    context as Activity

    val bottomSheetDialogController = rememberBottomSheetDialogController()

    Box(
        modifier = modifier
    ) {
        ProfilePicture(
            pictureUri = pictureUri,
            modifier = Modifier.fillMaxSize()
        )

        if (pictureChangeEnabled) {
            Icon(
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = R.string.tx_change_profile_photo.value,
                modifier = Modifier
                    .size(38.dp)
                    .align(Alignment.BottomEnd)
                    .clip(MaterialTheme.mShapes.full)
                    .background(
                        color = Color.White,
                        shape = MaterialTheme.shapes.full
                    )
                    .clickable(
                        shape = MaterialTheme.shapes.full,
                        onClick = bottomSheetDialogController::show
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.full
                    )
                    .padding(10.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

    PictureBottomSheetDialog(
        controller = bottomSheetDialogController,
        onPictureChange = onPictureChange
    )
}