package net.jsoft.daruj.create_account.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim
import net.jsoft.daruj.common.presentation.ui.theme.shape
import net.jsoft.daruj.common.util.clickable

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    pictureChangeEnabled: Boolean = false,
    onPictureChange: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shape.rounded100p
            )
    ) {
        Image(
            painter = painter ?: painterResource(R.drawable.ic_logo),
            contentDescription = "Placeholder",
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shape.rounded100p)
                .padding(if (painter == null) 12.dp else 0.dp),
            colorFilter = if (painter == null) {
                ColorFilter.tint(
                    color = MaterialTheme.colorScheme.onSurfaceDim
                )
            } else {
                null
            }
        )

        if(pictureChangeEnabled) {
            Icon(
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = "Camera",
                modifier = Modifier
                    .size(38.dp)
                    .align(Alignment.BottomEnd)
                    .clip(MaterialTheme.shape.rounded100p)
                    .background(
                        color = Color.White,
                        shape = MaterialTheme.shape.rounded100p
                    )
                    .clickable(
                        shape = MaterialTheme.shape.rounded100p,
                        onClick = onPictureChange
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shape.rounded100p
                    )
                    .padding(10.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
fun ProfilePicturePreview() {
    DarujTheme {
        ProfilePicture(
            pictureChangeEnabled = true
        )
    }
}