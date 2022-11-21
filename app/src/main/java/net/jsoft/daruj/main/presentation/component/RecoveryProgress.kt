package net.jsoft.daruj.main.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.Sex.Female
import net.jsoft.daruj.common.domain.model.Sex.Male
import net.jsoft.daruj.common.domain.model.daysUntilRecovery
import net.jsoft.daruj.common.presentation.ui.theme.full
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.util.getValue
import net.jsoft.daruj.common.util.indicationlessClickable
import net.jsoft.daruj.common.util.showShortToast
import kotlin.math.roundToInt

@Composable
fun RecoveryProgress(
    localUser: LocalUser,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val totalDays = when (localUser.mutable.sex) {
        Male -> 90
        Female -> 120
    }

    val progress = 1 - localUser.daysUntilRecovery / totalDays.toFloat()

    Column(
        modifier = modifier
            .indicationlessClickable {
                val message = if (progress == 1f) {
                    R.string.tx_you_can_donate_blood.getValue(context)
                } else {
                    context.resources.getQuantityString(
                        R.plurals.tx_you_will_be_able_to_donate_blood_in,
                        localUser.daysUntilRecovery,
                        localUser.daysUntilRecovery
                    )
                }

                context.showShortToast(message)
            },
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(0.5f)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.mShapes.full
                )
                .clip(MaterialTheme.mShapes.full)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(progress)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .align(Alignment.BottomCenter)
            )
        }

        Text(
            text = "${(progress * 100).roundToInt()}%",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelSmall
        )
    }
}