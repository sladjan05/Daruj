package net.jsoft.daruj.introduction.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.common.util.setScreenContent
import net.jsoft.daruj.introduction.presentation.screen.IntroductionScreen

@AndroidEntryPoint
class IntroductionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScreenContent { IntroductionScreen() }
    }
}