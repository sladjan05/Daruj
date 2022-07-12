package net.jsoft.daruj.auth.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.auth.presentation.screen.AuthScreen
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DarujTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AuthScreen()
                }
            }
        }
    }
}