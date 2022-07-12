package net.jsoft.daruj.common.util

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
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