package net.jsoft.daruj.common.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.utils.clickable
import net.jsoft.daruj.common.utils.indicationlessClickable
import net.jsoft.daruj.common.utils.value

@Composable
fun CheckBox(
    text: String,
    checked: Boolean,
    onCheck: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CheckBoxIcon(
            checked = checked,
            onCheck = onCheck
        )

        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .indicationlessClickable(onCheck),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun CheckBoxIcon(
    checked: Boolean,
    onCheck: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(25.dp)
            .clickable(
                shape = MaterialTheme.mShapes.small,
                onClick = onCheck
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.mShapes.small
            )
            .padding(4.dp)
    ) {
        AnimatedVisibility(
            visible = checked,
            modifier = Modifier.fillMaxSize(),
            enter = expandIn(expandFrom = Alignment.Center),
            exit = shrinkOut(shrinkTowards = Alignment.Center)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_check),
                modifier = Modifier.fillMaxSize(),
                contentDescription = R.string.tx_check.value,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}