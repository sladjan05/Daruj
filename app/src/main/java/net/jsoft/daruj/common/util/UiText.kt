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

    override fun toString(): String = when(this) {
        is StringResource -> "StringResource{$resId}"
        is DynamicString -> string
    }

    companion object {
        fun String.asUiText(): UiText = DynamicString(this)
        fun Int.asUiText(vararg args: Any): UiText = StringResource(this, args)
    }
}