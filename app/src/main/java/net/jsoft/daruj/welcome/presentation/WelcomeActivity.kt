package net.jsoft.daruj.welcome.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.common.utils.setScreenContent
import net.jsoft.daruj.welcome.presentation.screen.WelcomeScreen

@AndroidEntryPoint
class WelcomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setScreenContent {
            val systemUiController = rememberSystemUiController()

            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = true
            )

            systemUiController.setNavigationBarColor(
                color = Color.Transparent,
                darkIcons = false
            )

            WelcomeScreen()
        }
    }
}