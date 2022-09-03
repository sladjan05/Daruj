package net.jsoft.daruj.donate_blood.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.common.utils.setScreenContent
import net.jsoft.daruj.donate_blood.presentation.screen.DonateBloodScreen

@AndroidEntryPoint
class DonateBloodActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScreenContent { DonateBloodScreen() }
    }

    companion object {
        const val POST_ID = "POST_ID"
    }
}