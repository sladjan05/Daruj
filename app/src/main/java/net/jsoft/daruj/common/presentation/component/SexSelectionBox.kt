package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.jsoft.daruj.R
import net.jsoft.daruj.common.domain.model.Sex
import net.jsoft.daruj.common.util.value

@Composable
fun SexSelectionBox(
    sex: Sex,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    onSelected: (sex: Sex) -> Unit = {}
) {
    val sexes = buildList<@Composable BoxScope.() -> Unit> {
        Sex.values().forEach { sex ->
            add {
                Icon(
                    imageVector = when (sex) {
                        Sex.MALE -> Icons.Default.Male
                        Sex.FEMALE -> Icons.Default.Female
                    },
                    contentDescription = "Sex",
                    tint = when (sex) {
                        Sex.MALE -> MaterialTheme.colorScheme.primary
                        Sex.FEMALE -> MaterialTheme.colorScheme.secondary
                    }
                )
            }
        }
    }

    ProfilePropertiesDropdownSelectionBox(
        selectedIndex = sex.ordinal,
        label = when (sex) {
            Sex.MALE -> R.string.tx_man.value
            Sex.FEMALE -> R.string.tx_woman.value
        },
        items = sexes,
        rows = 1,
        columns = 2,
        modifier = modifier,
        expanded = expanded,
        enabled = enabled,
        labelColor = when (sex) {
            Sex.MALE -> MaterialTheme.colorScheme.primary
            Sex.FEMALE -> MaterialTheme.colorScheme.secondary
        },
        onClick = onClick,
        onSelected = { index ->
            onSelected(Sex.values()[index])
        }
    )
}