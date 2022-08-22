package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.presentation.ui.theme.bloodRh
import net.jsoft.daruj.common.presentation.ui.theme.bloodType
import net.jsoft.daruj.common.utils.value

@Composable
fun BloodSelectionBox(
    selectedBlood: Blood,
    onClick: () -> Unit,
    onSelected: (blood: Blood) -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    enabled: Boolean = true
) {
    val types = Blood.Type.values()
    val rhs = Blood.RH.values()

    ProfilePropertiesSelectionBox(
        selectedIndex = selectedBlood.type.ordinal + selectedBlood.rh.ordinal * 4,
        label = R.string.tx_blood_type.value,
        rows = 2,
        columns = 4,
        onClick = onClick,
        onSelected = { index ->
            val blood = Blood(
                type = Blood.Type.values()[index % 4],
                rh = Blood.RH.values()[index / 4]
            )

            onSelected(blood)
        },
        modifier = modifier,
        expanded = expanded,
        enabled = enabled,
        expandToRight = false,
        labelColor = MaterialTheme.colorScheme.primary
    ) { index ->
        val type = types[index % 4]
        val rh = rhs[index / 4]

        Row(
            modifier = Modifier.offset(y = 2.dp)
        ) {
            Text(
                text = type.toString(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bloodType
            )

            Text(
                text = rh.toString(),
                modifier = Modifier.offset(x = 1.dp),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bloodRh
            )
        }
    }
}