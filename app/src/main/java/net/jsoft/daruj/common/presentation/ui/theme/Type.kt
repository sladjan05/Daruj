package net.jsoft.daruj.common.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.util.size
import net.jsoft.daruj.common.util.weight

val Roboto = FontFamily(
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
    ),
    Font(
        resId = R.font.ft_roboto_thin,
        weight = FontWeight.Thin
    )
)

val Cookie = FontFamily(
    Font(
        resId = R.font.ft_cookie,
        weight = FontWeight.Normal
    )
)

val DarujTypography = Typography(
    labelMedium = Roboto weight FontWeight.Normal size 18.sp,
    labelSmall = Roboto weight FontWeight.Medium size 11.sp,

    bodyLarge = Roboto weight FontWeight.Normal size 20.sp,
    bodyMedium = Roboto weight FontWeight.Normal size 16.sp,
    bodySmall = Roboto weight FontWeight.Normal size 14.sp,

    headlineSmall = Roboto weight FontWeight.Medium size 24.sp,

    titleMedium = Roboto weight FontWeight.Medium size 22.sp
)

val Typography.bodySmaller: TextStyle
    get() = Roboto weight FontWeight.Normal size 10.sp

val Typography.bloodType: TextStyle
    get() = Cookie weight FontWeight.Light size 28.sp

val Typography.bloodRh: TextStyle
    get() = Cookie weight FontWeight.Light size 20.sp