package net.jsoft.daruj.common.util

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme

fun Modifier.thenIf(
    statement: Boolean,
    modifier: Modifier
): Modifier =
    if (statement) {
        then(modifier)
    } else {
        this
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

fun Modifier.clickableIf(
    clickable: Boolean,
    shape: Shape,
    onClick: () -> Unit
): Modifier = thenIf(
    statement = clickable,
    modifier = Modifier.clickable(shape, onClick)
)

val Int.value: String
    @Composable get() = stringResource(this)

val List<Int>.values: List<String>
    @Composable get() = map { resId -> resId.value }

fun List<Int>.getValues(context: Context) = map { resId -> context.getString(resId) }

@Composable
fun <T> rememberMutableStateOf(initialValue: T): MutableState<T> = remember {
    mutableStateOf(initialValue)
}

@Composable
fun rememberMutableInteractionSource(): MutableInteractionSource = remember {
    MutableInteractionSource()
}

@Composable
fun rememberSnackbarHostState(): SnackbarHostState = remember {
    SnackbarHostState()
}

@Composable
fun rememberFocusRequester(): FocusRequester = remember {
    FocusRequester()
}

fun ComponentActivity.setScreenContent(screen: @Composable () -> Unit) = setContent {
    DarujTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            screen()
        }
    }
}