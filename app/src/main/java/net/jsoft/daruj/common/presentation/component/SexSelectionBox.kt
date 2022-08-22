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
import net.jsoft.daruj.common.utils.value

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
            Sex.MALE -> R.string.tx_man.value
            Sex.FEMALE -> R.string.tx_woman.value
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
            Sex.MALE -> MaterialTheme.colorScheme.primary
            Sex.FEMALE -> MaterialTheme.colorScheme.secondary
        }
    ) { index ->
        val sex = sexes[index]

        Icon(
            imageVector = when (sex) {
                Sex.MALE -> Icons.Default.Male
                Sex.FEMALE -> Icons.Default.Female
            },
            contentDescription = when (sex) {
                Sex.MALE -> R.string.tx_man.value
                Sex.FEMALE -> R.string.tx_woman.value
            },
            tint = when (sex) {
                Sex.MALE -> MaterialTheme.colorScheme.primary
                Sex.FEMALE -> MaterialTheme.colorScheme.secondary
            }
        )
    }
}