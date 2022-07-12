package net.jsoft.daruj.common.util

import androidx.annotation.DrawableRes
import net.jsoft.daruj.R

enum class Language(
    val language: String,
    val code: String,
    val locale: String,
    @DrawableRes val flagResId: Int
) {
    SERBIAN(
        language = "Srpski",
        code = "SR",
        locale = "sr-rBA",
        flagResId = R.drawable.ic_sr_flag
    ),

    ENGLISH(
        language = "English",
        code = "EN",
        locale = "en-rUS",
        flagResId = R.drawable.ic_usa_flag
    )
}