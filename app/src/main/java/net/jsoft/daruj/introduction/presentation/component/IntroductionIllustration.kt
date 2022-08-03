package net.jsoft.daruj.introduction.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme
import net.jsoft.daruj.common.util.value

private val HEIGHT = 320.dp
private val ILLUSTRATION_HEIGHT = 140.dp
private val DESCRIPTION_WIDTH = 265.dp

private val TITLE_WIDTH = 280.dp

@Composable
fun IntroductionIllustration(
    painter: Painter,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(HEIGHT),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.height(ILLUSTRATION_HEIGHT)
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = title,
            modifier = Modifier.widthIn(max = TITLE_WIDTH),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = description,
            modifier = Modifier.widthIn(max = DESCRIPTION_WIDTH),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IntroductionIllustrationPreview() {
    DarujTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            IntroductionIllustration(
                painter = painterResource(R.drawable.ic_tutorial_illustration_1),
                title = R.string.tx_tutorial_illustration_title_1.value,
                description = R.string.tx_tutorial_illustration_description_1.value,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}