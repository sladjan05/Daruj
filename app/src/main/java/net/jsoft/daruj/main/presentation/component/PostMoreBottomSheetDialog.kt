package net.jsoft.daruj.main.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.component.BottomSheetDialog
import net.jsoft.daruj.common.presentation.component.BottomSheetDialogController
import net.jsoft.daruj.common.util.clickable
import net.jsoft.daruj.common.util.value

@Composable
fun PostMoreBottomSheetDialog(
    controller: BottomSheetDialogController,
    onModifyClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    BottomSheetDialog(controller) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        shape = RectangleShape,
                        onClick = {
                            controller.dismiss()
                            onModifyClick()
                        }
                    )
                    .padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_modify),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = R.string.tx_modify_post.value,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        shape = RectangleShape,
                        onClick = {
                            controller.dismiss()
                            onDeleteClick()
                        }
                    )
                    .padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = R.string.tx_delete_post.value,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}