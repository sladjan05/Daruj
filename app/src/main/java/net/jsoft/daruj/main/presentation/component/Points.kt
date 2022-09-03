package net.jsoft.daruj.main.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.ui.theme.mShapes

@Composable
fun Points(
    points: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.mShapes.medium
            )
            .clip(MaterialTheme.mShapes.medium)
            .padding(
                horizontal = 14.dp,
                vertical = 10.dp
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.height(25.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = points.toString(),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelMedium
        )
    }
}