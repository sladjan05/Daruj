package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.presentation.ui.theme.bloodRh
import net.jsoft.daruj.common.presentation.ui.theme.bloodType
import net.jsoft.daruj.common.util.value

@Composable
fun BloodSelectionBox(
    blood: Blood,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    onSelected: (blood: Blood) -> Unit = {}
) {
    val types = Blood.Type.values()
    val rhs = Blood.RH.values()

    val bloods = buildList<@Composable BoxScope.() -> Unit> {
        rhs.forEach { rh ->
            types.forEach { type ->
                add {
                    Row(
                        modifier = Modifier.offset(y = 2.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = when (type) {
                                Blood.Type.ZERO -> "0"
                                Blood.Type.A -> "A"
                                Blood.Type.B -> "B"
                                Blood.Type.AB -> "AB"
                            },
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bloodType
                        )

                        Text(
                            text = when (rh) {
                                Blood.RH.POSITIVE -> "+"
                                Blood.RH.NEGATIVE -> "-"
                            },
                            modifier = Modifier.offset(x = 1.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bloodRh
                        )
                    }
                }
            }
        }
    }

    ProfilePropertiesDropdownSelectionBox(
        selectedIndex = blood.type.ordinal + blood.rh.ordinal * 4,
        label = R.string.tx_blood_type.value,
        items = bloods,
        rows = 2,
        columns = 4,
        modifier = modifier,
        expanded = expanded,
        enabled = enabled,
        labelColor = MaterialTheme.colorScheme.primary,
        onClick = onClick,
        onSelected = { index ->
            onSelected(
                Blood(
                    type = Blood.Type.values()[index % 4],
                    rh = Blood.RH.values()[index / 4]
                )
            )
        }
    )
}