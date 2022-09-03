package net.jsoft.daruj.start.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.jsoft.daruj.auth.presentation.AuthActivity
import net.jsoft.daruj.common.utils.switchActivity
import net.jsoft.daruj.create_account.presentation.CreateAccountActivity
import net.jsoft.daruj.main.presentation.MainActivity
import net.jsoft.daruj.start.presentation.viewmodel.StartingTask
import net.jsoft.daruj.start.presentation.viewmodel.StartingViewModel
import net.jsoft.daruj.welcome.presentation.WelcomeActivity

@AndroidEntryPoint
class StartingActivity : ComponentActivity() {
    private val viewModel by viewModels<StartingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            viewModel.isLoading
        }

        lifecycleScope.launch {
            viewModel.taskFlow.collectLatest { task ->
                when (task) {
                    is StartingTask.ShowWelcomeScreen -> switchActivity<WelcomeActivity>()
                    is StartingTask.ShowAuthScreen -> switchActivity<AuthActivity>()
                    is StartingTask.ShowCreateAccountScreen -> switchActivity<CreateAccountActivity>()
                    is StartingTask.ShowMainScreen -> switchActivity<MainActivity>()
                }
            }
        }
    }
}