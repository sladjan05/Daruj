package net.jsoft.daruj.introduction.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.common.utils.setScreenContent
import net.jsoft.daruj.introduction.presentation.screen.IntroductionScreen

@AndroidEntryPoint
class IntroductionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScreenContent { IntroductionScreen() }
    }
}