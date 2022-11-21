package net.jsoft.daruj.common.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.component.AnimatedClickableIcon
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.util.goBack
import net.jsoft.daruj.common.util.value

@Composable
fun ScreenWithBackAndTitle(
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable BoxScope.() -> Unit
) {
    val context = LocalContext.current
    context as Activity

    BackHandler { if (enabled) context.goBack() }

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = MaterialTheme.spacing.large)
        ) {
            AnimatedClickableIcon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = R.string.tx_back.value,
                onClick = context::goBack,
                modifier = Modifier
                    .size(22.dp)
                    .align(Alignment.CenterStart),
                enabled = enabled,
                tint = MaterialTheme.colorScheme.primary,
                scaleTo = 0.6f
            )

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.large))

            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            content = content
        )
    }
}