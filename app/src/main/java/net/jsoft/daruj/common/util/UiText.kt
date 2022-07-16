package net.jsoft.daruj.common.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    class DynamicString(val string: String) : UiText()

    class StringResource(
        @StringRes
        val resId: Int,
        vararg val args: Any
    ) : UiText()

    val value: String
        @Composable
        get() = when (this) {
            is DynamicString -> string

            is StringResource -> stringResource(
                id = resId,
                formatArgs = args
            )
        }

    fun getValue(context: Context): String = when (this) {
        is DynamicString -> string
        is StringResource -> context.getString(resId, args)
    }

    override fun toString(): String = when (this) {
        is StringResource -> "StringResource{$resId}"
        is DynamicString -> string
    }
}

fun String.asUiText(): UiText = UiText.DynamicString(this)
@JvmName("asUiTextListString")
fun List<String>.asUiText(): List<UiText> = map { string -> string.asUiText() }

fun Int.asUiText(vararg args: Any): UiText = UiText.StringResource(this, args)
@JvmName("asUiTextListInt")
fun List<Int>.asUiText(): List<UiText> = map { int -> int.asUiText() }



val List<UiText>.value: List<String>
    @Composable
    get() = map { uiText -> uiText.value }

fun List<UiText>.getValues(context: Context): List<String> = map { uiText ->
    uiText.getValue(context)
}