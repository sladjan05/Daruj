package net.jsoft.daruj.create_account.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.common.utils.setScreenContent
import net.jsoft.daruj.create_account.presentation.screen.CreateAccountScreen

@AndroidEntryPoint
class CreateAccountActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScreenContent { CreateAccountScreen() }
    }
}