package net.jsoft.daruj.common.misc

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

sealed class UiText {
    class DynamicString(val string: String) : UiText()

    class StringResource(
        @StringRes
        val resId: Int,
        vararg val args: Any
    ) : UiText()

    class QuantityString(
        @PluralsRes
        val resId: Int,
        val quantity: Int,
        vararg var args: Any
    ) : UiText()

    val value: String
        @Composable
        get() = getValue(LocalContext.current)

    fun getValue(context: Context): String = when (this) {
        is DynamicString -> string
        is StringResource -> context.getString(resId, args)
        is QuantityString -> context.resources.getQuantityString(resId, quantity, *args)
    }

    override fun toString(): String = when (this) {
        is DynamicString -> string
        is StringResource -> "StringResource{$resId}"
        is QuantityString -> "QuantityString{$resId}"
    }

    companion object {
        val Empty get() = DynamicString("")
    }
}

fun String.asUiText() = UiText.DynamicString(this)

@JvmName("ListStringasUiText")
fun List<String>.asUiText(): List<UiText> = map { string -> string.asUiText() }

fun Int.asUiText(vararg args: Any) = UiText.StringResource(this, args)

@JvmName("ListIntasUiText")
fun List<Int>.asUiText(): List<UiText> = map { int -> int.asUiText() }

val List<UiText>.value: List<String>
    @Composable get() = getValues(LocalContext.current)

fun List<UiText>.getValues(context: Context): List<String> = map { uiText -> uiText.getValue(context) }