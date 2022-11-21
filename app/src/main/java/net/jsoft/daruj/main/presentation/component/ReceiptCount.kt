package net.jsoft.daruj.main.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.jsoft.daruj.common.presentation.ui.theme.Roboto
import net.jsoft.daruj.common.presentation.ui.theme.full
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.util.size
import net.jsoft.daruj.common.util.weight

private val RobotoMedium8 = Roboto weight FontWeight.Medium size 8.sp

@Composable
fun ReceiptCount(
    count: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(13.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.mShapes.full
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (count < 10) count.toString() else "9+",
            color = MaterialTheme.colorScheme.onSecondary,
            style = RobotoMedium8
        )
    }
}