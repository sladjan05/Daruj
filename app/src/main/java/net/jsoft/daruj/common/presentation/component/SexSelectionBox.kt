package net.jsoft.daruj.common.presentation.component

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
    selectedSex: Sex,
    onClick: () -> Unit,
    onSelected: (sex: Sex) -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    enabled: Boolean = true
) {
    val sexes = Sex.values()

    ProfilePropertiesSelectionBox(
        selectedIndex = selectedSex.ordinal,
        label = when (selectedSex) {
            Sex.Male -> R.string.tx_man.value
            Sex.Female -> R.string.tx_woman.value
        },
        rows = 1,
        columns = 2,
        onClick = onClick,
        onSelected = { index ->
            onSelected(sexes[index])
        },
        modifier = modifier,
        expanded = expanded,
        enabled = enabled,
        labelColor = when (selectedSex) {
            Sex.Male -> MaterialTheme.colorScheme.primary
            Sex.Female -> MaterialTheme.colorScheme.secondary
        }
    ) { index ->
        val sex = sexes[index]

        Icon(
            imageVector = when (sex) {
                Sex.Male -> Icons.Default.Male
                Sex.Female -> Icons.Default.Female
            },
            contentDescription = when (sex) {
                Sex.Male -> R.string.tx_man.value
                Sex.Female -> R.string.tx_woman.value
            },
            tint = when (sex) {
                Sex.Male -> MaterialTheme.colorScheme.primary
                Sex.Female -> MaterialTheme.colorScheme.secondary
            }
        )
    }
}