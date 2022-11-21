package net.jsoft.daruj.auth.presentation.screen

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.auth.presentation.viewmodel.AuthTask
import net.jsoft.daruj.auth.presentation.viewmodel.AuthViewModel
import net.jsoft.daruj.common.presentation.screen.ScreenWithSnackbars
import net.jsoft.daruj.common.util.rememberSnackbarHostState

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    val navController = rememberAnimatedNavController()

    val errorHostState = rememberSnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is AuthTask.ShowError -> errorHostState.showSnackbar(task.message.getValue(context))
            }
        }
    }

    ScreenWithSnackbars(
        modifier = Modifier.fillMaxSize(),
        errorHostState = errorHostState
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = Screen.Phone.route,
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            composable(
                route = Screen.Phone.route,
                enterTransition = {
                    when (initialState.destination.route) {
                        Screen.Verification.route -> slideInHorizontally { -it }
                        else -> EnterTransition.None
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        Screen.Verification.route -> slideOutHorizontally { -it }
                        else -> ExitTransition.None
                    }
                }
            ) {
                PhoneNumberScreen(navController = navController)
            }

            composable(
                route = Screen.Verification.route,
                arguments = listOf(
                    navArgument(Screen.Verification.FULL_PHONE_NUMBER) {
                        type = NavType.StringType
                    }
                ),
                enterTransition = {
                    when (initialState.destination.route) {
                        Screen.Phone.route -> slideInHorizontally { it }
                        else -> EnterTransition.None
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        Screen.Phone.route -> slideOutHorizontally { it }
                        else -> ExitTransition.None
                    }
                }
            ) {
                VerificationCodeScreen()
            }
        }
    }
}