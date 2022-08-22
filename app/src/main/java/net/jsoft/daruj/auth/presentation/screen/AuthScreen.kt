package net.jsoft.daruj.auth.presentation.screen

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import net.jsoft.daruj.auth.presentation.viewmodel.AuthTask
import net.jsoft.daruj.auth.presentation.viewmodel.AuthViewModel
import net.jsoft.daruj.common.presentation.component.ErrorInfoSnackbars
import net.jsoft.daruj.common.utils.rememberSnackbarHostState

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    val navController = rememberAnimatedNavController()

    val hostState = rememberSnackbarHostState()
    val errorHostState = rememberSnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collect { task ->
            when (task) {
                is AuthTask.ShowInfo -> hostState.showSnackbar(task.message.getValue(context))
                is AuthTask.ShowError -> errorHostState.showSnackbar(task.message.getValue(context))
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
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

        ErrorInfoSnackbars(
            infoHostState = hostState,
            errorHostState = errorHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}