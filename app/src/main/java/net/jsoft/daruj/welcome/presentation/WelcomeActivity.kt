package net.jsoft.daruj.welcome.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme
import net.jsoft.daruj.introduction.presentation.screen.IntroductionScreen
import net.jsoft.daruj.welcome.presentation.screen.WelcomeScreen

@AndroidEntryPoint
class WelcomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DarujTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    WelcomeScreen()
                }
            }
        }
    }
}