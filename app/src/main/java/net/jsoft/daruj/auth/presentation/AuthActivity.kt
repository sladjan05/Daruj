package net.jsoft.daruj.auth.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.auth.presentation.screen.AuthScreen
import net.jsoft.daruj.common.utils.setScreenContent

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScreenContent { AuthScreen() }
    }
}