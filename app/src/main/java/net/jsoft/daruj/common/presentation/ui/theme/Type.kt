package net.jsoft.daruj.common.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import net.jsoft.daruj.R

private val Roboto = FontFamily(
    Font(
        resId = R.font.ft_roboto_regular,
        weight = FontWeight.Normal
    ),

    Font(
        resId = R.font.ft_roboto_medium,
        weight = FontWeight.Medium
    ),

    Font(
        resId = R.font.ft_roboto_light,
        weight = FontWeight.Light
    )
)

private val Cookie = FontFamily(
    Font(
        resId = R.font.ft_cookie,
        weight = FontWeight.Normal
    )
)

val Typography = Typography(
    titleSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 26.sp
    ),

    labelSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),

    bodySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)

val Typography.bloodType: TextStyle
    get() = TextStyle(
        fontFamily = Cookie,
        fontWeight = FontWeight.Light,
        fontSize = 28.sp
    )

val Typography.bloodRh: TextStyle
    get() = TextStyle(
        fontFamily = Cookie,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp
    )