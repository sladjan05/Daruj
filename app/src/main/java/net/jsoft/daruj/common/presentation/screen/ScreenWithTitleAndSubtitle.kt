package net.jsoft.daruj.common.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.jsoft.daruj.common.presentation.component.TitleSubtitle
import net.jsoft.daruj.common.presentation.ui.theme.spacing

@Composable
fun ScreenWithTitleAndSubtitle(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    screenBox: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = MaterialTheme.spacing.huge),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraLarge)
        ) {
            TitleSubtitle(
                title = title,
                subtitle = subtitle,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                content = content
            )
        }

        screenBox()
    }
}