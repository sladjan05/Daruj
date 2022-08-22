package net.jsoft.daruj.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.common.utils.setLocale
import net.jsoft.daruj.common.utils.setScreenContent
import net.jsoft.daruj.main.presentation.screen.MainScreen
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale(Locale("sr"))

        super.onCreate(savedInstanceState)
        setScreenContent { MainScreen() }
    }
}