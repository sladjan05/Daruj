package net.jsoft.daruj.common.presentation.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
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
import net.jsoft.daruj.common.presentation.ui.theme.full
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.utils.value

@Composable
fun ProfilePicture(
    pictureUri: Uri?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.mShapes.full
            ),
        contentAlignment = Alignment.Center
    ) {
        var isLoaded by rememberSaveable {
            mutableStateOf(false)
        }

        AsyncImage(
            model = pictureUri,
            contentDescription = R.string.tx_profile_picture.value,
            modifier = Modifier
                .fillMaxHeight(if (isLoaded) 1f else 0.7f)
                .clip(MaterialTheme.mShapes.full),
            contentScale = if (isLoaded) {
                ContentScale.Crop
            } else {
                ContentScale.Fit
            },
            error = painterResource(R.drawable.ic_logo),
            placeholder = painterResource(R.drawable.ic_logo),
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