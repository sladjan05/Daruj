package net.jsoft.daruj.main.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.component.TextBox

@Composable
fun SearchBar(
    text: String,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    enabled: Boolean = true
) {
    TextBox(
        text = text,
        onValueChange = onValueChange,
        modifier = modifier,
        hint = hint,
        enabled = enabled,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}