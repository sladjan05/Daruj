package net.jsoft.daruj.main.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.UCropContract
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.presentation.component.PrimaryIconButton
import net.jsoft.daruj.common.presentation.component.ProfilePicture
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.util.*
import net.jsoft.daruj.main.domain.model.Receipt

@Composable
fun Receipt(
    receipt: Receipt,
    onApproveClick: () -> Unit,
    onDenyClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 13.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfilePicture(
                pictureUri = receipt.user.pictureUri,
                modifier = Modifier.size(40.dp)
            )

            Column {
                Text(
                    text = receipt.user.displayName,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = RobotoRegular14
                )

                Text(
                    text = run {
                        val timestamp by formatTimestamp(receipt.timestamp).collectAsState(UiText.Empty)
                        timestamp.value
                    },
                    color = MaterialTheme.colorScheme.onBackground,
                    style = RobotoLight12
                )
            }
        }

        var isLoaded by rememberMutableStateOf(false)

        AsyncImage(
            model = receipt.pictureUri,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(UCropContract.ReceiptRatio().ratio)
                .thenIf(
                    statement = !isLoaded,
                    ifModifier = Modifier.padding(70.dp),
                    elseModifier = Modifier.padding(horizontal = 5.dp)
                )
                .clip(MaterialTheme.mShapes.medium),
            contentScale = if (isLoaded) ContentScale.Crop else ContentScale.Fit,
            error = painterResource(R.drawable.ic_full_logo),
            placeholder = painterResource(R.drawable.ic_full_logo),
            onSuccess = { isLoaded = true },
            onLoading = { isLoaded = false },
            colorFilter = isLoaded.ifFalse { ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface) }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PrimaryIconButton(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = R.string.tx_approve.value,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                onClick = onApproveClick,
                modifier = Modifier.weight(1f),
                enabled = enabled
            )

            PrimaryIconButton(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_cancel),
                        contentDescription = R.string.tx_deny.value,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                },
                onClick = onDenyClick,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(1f),
                enabled = enabled
            )
        }
    }
}