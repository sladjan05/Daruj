package net.jsoft.daruj.common.utils

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import net.jsoft.daruj.common.presentation.component.BottomSheetDialogController
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme

fun Modifier.thenIf(
    statement: Boolean,
    ifModifier: Modifier,
    elseModifier: Modifier = this
): Modifier =
    if (statement) {
        then(ifModifier)
    } else {
        elseModifier
    }

fun Modifier.clickable(
    shape: Shape,
    onClick: () -> Unit
): Modifier = composed {
    clip(shape).clickable(
        interactionSource = rememberMutableInteractionSource(),
        indication = rememberRipple(),
        onClick = onClick
    )
}

fun Modifier.clickable(
    ripple: Indication,
    onClick: () -> Unit
): Modifier = composed {
    clickable(
        interactionSource = rememberMutableInteractionSource(),
        indication = ripple,
        onClick = onClick
    )
}

fun Modifier.clickableIf(
    clickable: Boolean,
    shape: Shape,
    onClick: () -> Unit
): Modifier = thenIf(
    statement = clickable,
    ifModifier = Modifier.clickable(shape, onClick)
)

fun Modifier.clickableIf(
    clickable: Boolean,
    ripple: Indication,
    onClick: () -> Unit
): Modifier = thenIf(
    statement = clickable,
    ifModifier = clickable(ripple, onClick)
)

fun Modifier.indicationlessClickable(
    onClick: () -> Unit
): Modifier = composed {
    Modifier.clickable(
        interactionSource = rememberMutableInteractionSource(),
        indication = null,
        onClick = onClick
    )
}

val Int.value: String
    @Composable get() = stringResource(this)

fun Int.getValue(context: Context) = context.getString(this)

val List<Int>.values: List<String>
    @Composable get() = getValues(LocalContext.current)

fun List<Int>.getValues(context: Context) = map { resId -> resId.getValue(context) }

@Composable
fun <T> rememberMutableStateOf(initialValue: T) = remember { mutableStateOf(initialValue) }

@Composable
fun rememberMutableInteractionSource() = remember { MutableInteractionSource() }

@Composable
fun rememberSnackbarHostState() = remember { SnackbarHostState() }

@Composable
fun rememberFocusRequester() = remember { FocusRequester() }

@Composable
fun rememberBottomSheetDialogController(): BottomSheetDialogController {
    val context = LocalContext.current
    context as AppCompatActivity

    return remember { BottomSheetDialogController(context) }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun NoOverscrollEffect(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null,
        content = content
    )
}

fun ComponentActivity.setScreenContent(screen: @Composable () -> Unit) = setContent {
    DarujTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            content = screen
        )
    }
}