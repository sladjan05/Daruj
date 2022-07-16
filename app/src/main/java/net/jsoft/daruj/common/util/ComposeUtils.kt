package net.jsoft.daruj.common.util

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.SnackbarHostState
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

fun Modifier.clickable(shape: Shape, onClick: () -> Unit): Modifier =
    composed {
        clip(shape).clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(),
            onClick = onClick
        )
    }

fun Modifier.clickableIf(clickable: Boolean, shape: Shape, onClick: () -> Unit): Modifier =
    if (clickable) {
        clickable(shape, onClick)
    } else {
        Modifier
    }

val Int.value: String
    @Composable get() = stringResource(this)

fun Context.countriesSortedBySerbianAlphabet(): List<Country> {
    val alphabet = arrayOf(
        "A",
        "B",
        "C",
        "Č",
        "Ć",
        "D",
        "DŽ",
        "Đ",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "LJ",
        "M",
        "N",
        "NJ",
        "O",
        "P",
        "R",
        "S",
        "Š",
        "T",
        "U",
        "V",
        "Z",
        "Ž"
    )

    val countries = Country.values().sortedBy { country ->
        val countryName = getString(country.resId).uppercase()
        val countryModified = countryName
            .replace("DŽ", Char(6).toString())
            .replace("LJ", Char(16).toString())
            .replace("NJ", Char(19).toString())

        String(
            chars = countryModified.map { char ->
                if (char in arrayOf(Char(6), Char(16), Char(19))) {
                    char
                } else {
                    alphabet.indexOf(char.toString()).toChar()
                }
            }.toCharArray()
        )
    }

    return countries
}

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