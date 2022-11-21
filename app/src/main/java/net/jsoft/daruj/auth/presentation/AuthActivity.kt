package net.jsoft.daruj.auth.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.auth.presentation.screen.AuthScreen
import net.jsoft.daruj.common.util.setScreenContent

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScreenContent { AuthScreen() }
    }
}