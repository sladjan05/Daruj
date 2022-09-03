package net.jsoft.daruj.common.presentation.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.UCropActivityResultContract
import net.jsoft.daruj.common.utils.clickable
import net.jsoft.daruj.common.utils.createCacheFile
import net.jsoft.daruj.common.utils.value
import java.util.*

@Composable
fun PictureBottomSheetDialog(
    controller: BottomSheetDialogController,
    onPictureChange: (uri: Uri) -> Unit
) {
    val context = LocalContext.current

    val cropPictureLauncher = rememberLauncherForActivityResult(
        contract = UCropActivityResultContract()
    ) { uri ->
        if (uri != null) {
            controller.dismiss()
            onPictureChange(uri)
        }
    }

    val takenPictureUri = remember {
        val file = context.createCacheFile("${UUID.randomUUID()}.png")

        FileProvider.getUriForFile(
            context,
            "${context.applicationContext.packageName}.provider",
            file
        )
    }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { isTaken ->
        if (isTaken) cropPictureLauncher.launch(takenPictureUri)
    }

    val pickPictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) cropPictureLauncher.launch(uri)
    }

    BottomSheetDialog(controller) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val buttons = remember {
                arrayOf(
                    R.drawable.ic_camera to R.string.tx_take_picture_now,
                    R.drawable.ic_gallery to R.string.tx_choose_from_gallery
                )
            }

            buttons.forEachIndexed { index, (iconId, textId) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(RectangleShape) {
                            when (index) {
                                0 -> takePictureLauncher.launch(takenPictureUri)
                                1 -> pickPictureLauncher.launch("image/*")
                            }
                        }
                        .padding(
                            horizontal = 20.dp,
                            vertical = 18.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(iconId),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = textId.value,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}