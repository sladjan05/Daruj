package net.jsoft.daruj.main.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.main.domain.model.Donation
import net.jsoft.daruj.main.domain.model.fullName
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun Donation(
    donation: Donation,
    onPostClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(
            horizontal = 30.dp,
            vertical = 15.dp
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = donation.post.fullName,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium
            )

            val timestamp = donation.timestamp.withZoneSameLocal(ZoneId.systemDefault())

            Text(
                text = timestamp.format(DateTimeFormatter.ofPattern("dd. MM. uuuu.")),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall
            )
        }

        PrimaryButton(
            text = "OBJAVA",
            onClick = onPostClick,
            modifier = Modifier.width(100.dp)
        )
    }
}