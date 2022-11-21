package net.jsoft.daruj.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.common.util.setLocale
import net.jsoft.daruj.common.util.setScreenContent
import net.jsoft.daruj.main.presentation.screen.MainScreen
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale(Locale("sr"))

        super.onCreate(savedInstanceState)
        setScreenContent { MainScreen() }
    }
}