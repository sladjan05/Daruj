package net.jsoft.daruj.common.presentation.component

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim
import kotlin.time.Duration.Companion.seconds

class SnackbarState {
    var message by mutableStateOf<String?>(null)
        private set

    suspend fun showMesasge(message: String) {
        this.message = message
        delay(MESSAGE_TIME)
        this.message = null
    }

    companion object {
        private val MESSAGE_TIME = 1.5.seconds
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun PrimaryButtonSnackbar(
    text: String,
    onClick: () -> Unit,
    state: SnackbarState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable BoxScope.() -> Unit)? = null,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    snackbarColor: Color = MaterialTheme.colorScheme.surface,
    snackbarTextColor: Color = MaterialTheme.colorScheme.onSurfaceDim
) {
    SnackbarHostState()
    AnimatedContent(
        targetState = state.message,
        transitionSpec = {
            fadeIn() with fadeOut()
        }
    ) { targetMessage ->
        Button(
            onClick = if (targetMessage == null) {
                onClick
            } else {
                {}
            },
            modifier = modifier,
            shape = MaterialTheme.mShapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (targetMessage == null) color else snackbarColor,
                contentColor = if (targetMessage == null) textColor else snackbarTextColor,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = MaterialTheme.colorScheme.onSurface
            ),
            enabled = enabled,
            contentPadding = PaddingValues(
                vertical = 15.dp
            )
        ) {
            if (leadingIcon != null) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterVertically),
                    content = leadingIcon
                )

                Spacer(modifier = Modifier.width(15.dp))
            }

            Text(
                text = targetMessage ?: text,
                modifier = Modifier.align(Alignment.CenterVertically),
                color = if (targetMessage == null) textColor else snackbarTextColor,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}