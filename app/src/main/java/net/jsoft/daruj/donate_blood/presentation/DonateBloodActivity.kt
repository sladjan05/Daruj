package net.jsoft.daruj.donate_blood.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.common.util.setScreenContent
import net.jsoft.daruj.donate_blood.presentation.screen.DonateBloodScreen

@AndroidEntryPoint
class DonateBloodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScreenContent { DonateBloodScreen() }
    }

    companion object {
        const val Post = "post"
    }
}