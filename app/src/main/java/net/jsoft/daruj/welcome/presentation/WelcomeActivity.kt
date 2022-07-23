package net.jsoft.daruj.welcome.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.common.util.setScreenContent
import net.jsoft.daruj.welcome.presentation.screen.WelcomeScreen

@AndroidEntryPoint
class WelcomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setScreenContent {
            WelcomeScreen()
        }
    }
}