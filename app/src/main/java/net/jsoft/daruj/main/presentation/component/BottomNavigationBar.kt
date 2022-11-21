package net.jsoft.daruj.main.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.ui.theme.full
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.presentation.ui.theme.onBackgroundDim
import net.jsoft.daruj.common.util.indicationlessClickable
import net.jsoft.daruj.common.util.value

@Composable
fun BottomNavigationBar(
    currentIndex: Int,
    onChange: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(
        topStart = 15.dp,
        topEnd = 15.dp
    )

    BoxWithConstraints(
        modifier = modifier
            .height(60.dp)
            .shadow(
                elevation = 6.dp,
                shape = shape
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = shape
            )
            .clip(shape)
    ) {
        val offset by animateDpAsState(targetValue = maxWidth / 4 * currentIndex)

        Box(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .align(Alignment.TopStart)
                .offset(x = offset),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(3.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.mShapes.full
                    )
                    .align(Alignment.TopCenter)
            )
        }

        Row(
            modifier = Modifier
                .width(maxWidth)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val icons = arrayOf(
                R.drawable.ic_home to R.drawable.ic_home_filled,
                R.drawable.ic_search to R.drawable.ic_search_filled,
                R.drawable.ic_saved to R.drawable.ic_saved_filled,
                R.drawable.ic_profile to R.drawable.ic_profile_filled
            )

            val contentDescriptions = arrayOf(
                R.string.tx_home,
                R.string.tx_search,
                R.string.tx_saved,
                R.string.tx_profile
            )

            repeat(icons.size) { index ->
                val isSelected = index == currentIndex

                val iconColor by animateColorAsState(
                    targetValue = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackgroundDim
                    }
                )

                val iconScale by animateFloatAsState(targetValue = if (isSelected) 1.2f else 1f)

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .indicationlessClickable {
                            onChange(index)
                        }
                ) {
                    Icon(
                        painter = painterResource(if (index == currentIndex) icons[index].second else icons[index].first),
                        contentDescription = contentDescriptions[index].value,
                        modifier = Modifier
                            .height(22.dp)
                            .scale(iconScale)
                            .align(Alignment.Center),
                        tint = iconColor
                    )
                }
            }
        }
    }
}