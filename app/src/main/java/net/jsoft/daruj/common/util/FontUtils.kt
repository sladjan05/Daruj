package net.jsoft.daruj.common.util

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

infix fun FontFamily.weight(
    weight: FontWeight
) = TextStyle(
    fontFamily = this,
    fontWeight = weight
)

infix fun TextStyle.size(size: TextUnit) = copy(fontSize = size)