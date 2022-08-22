package net.jsoft.daruj.main.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.common.utils.value

@Composable
fun DonateButton(
    modifier: Modifier = Modifier
) {
    PrimaryButton(
        text = R.string.tx_donate_blood.value,
        onClick = {},
        modifier = modifier,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_heart),
                modifier = Modifier.size(20.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary
            )
        },
        color = MaterialTheme.colorScheme.secondary,
        textColor = MaterialTheme.colorScheme.onSecondary
    )
}