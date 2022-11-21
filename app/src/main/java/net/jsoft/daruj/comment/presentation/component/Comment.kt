package net.jsoft.daruj.comment.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.comment.domain.model.Comment
import net.jsoft.daruj.common.presentation.component.ProfilePicture
import net.jsoft.daruj.common.presentation.ui.theme.bodySmaller

@Composable
fun Comment(
    comment: Comment,
    modifier: Modifier = Modifier
) {
    val mutable = comment.mutable
    val immutable = comment.immutable

    val user = immutable.user

    Row(
        modifier = modifier.padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ProfilePicture(
            pictureUri = user.pictureUri,
            modifier = Modifier.size(40.dp)
        )

        Column {
            Text(
                text = user.displayName,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = mutable.content,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}