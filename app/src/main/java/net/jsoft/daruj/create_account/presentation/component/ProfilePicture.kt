package net.jsoft.daruj.create_account.presentation.component

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import kotlinx.coroutines.launch
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim
import net.jsoft.daruj.common.presentation.ui.theme.shape
import net.jsoft.daruj.common.util.FileUtils
import net.jsoft.daruj.common.util.clickable

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    bitmap: Bitmap? = null,
    pictureChangeEnabled: Boolean = false,
    onPictureChange: (uri: Uri) -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val file = FileUtils.from(context, uri)

            scope.launch {
                val compressedFile = Compressor.compress(context, file) {
                    quality(80)
                    format(Bitmap.CompressFormat.PNG)
                }

                onPictureChange(compressedFile.toUri())
            }
        }
    }

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shape.rounded100p
            )
    ) {
        if (bitmap == null) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "Placeholder",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shape.rounded100p)
                    .padding(12.dp),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurfaceDim)
            )
        } else {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Picture",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shape.rounded100p)
            )
        }

        if (pictureChangeEnabled) {
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
                    .clickable(MaterialTheme.shape.rounded100p) {
                        launcher.launch("image/*")
                    }
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