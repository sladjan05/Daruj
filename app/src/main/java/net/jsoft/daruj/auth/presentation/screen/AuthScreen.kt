package net.jsoft.daruj.auth.presentation.screen

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.viewmodel.AuthEvent
import net.jsoft.daruj.auth.presentation.viewmodel.AuthTask
import net.jsoft.daruj.auth.presentation.viewmodel.AuthViewModel
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.common.presentation.component.TextSnackbar
import net.jsoft.daruj.common.util.value

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberAnimatedNavController()
    val context = LocalContext.current

    val hostState = remember { SnackbarHostState() }
    val errorHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is AuthTask.Next -> when (viewModel.screen) {
                    is Screen.Phone -> {
                        navController.navigate(Screen.Phone.route)
                    }

                    is Screen.Verification -> {
                        navController.navigate(Screen.Verification.route)
                    }
                }

                is AuthTask.ShowError -> launch {
                    errorHostState.showSnackbar(task.message.getValue(context))
                }

                is AuthTask.ShowInfo -> launch {
                    hostState.showSnackbar(task.message.getValue(context))
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedNavHost(
                navController = navController,
                startDestination = Screen.Phone.route,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .heightIn(
                        min = 260.dp
                    ),
                contentAlignment = Alignment.TopCenter
            ) {
                composable(
                    route = Screen.Phone.route,
                    enterTransition = {
                        when (initialState.destination.route) {
                            Screen.Verification.route -> slideInHorizontally { -1000 }
                            else -> EnterTransition.None
                        }
                    },
                    exitTransition = {
                        when (targetState.destination.route) {
                            Screen.Verification.route -> slideOutHorizontally { -1000 }
                            else -> ExitTransition.None
                        }
                    }
                ) {
                    PhoneNumberScreen(
                        viewModel = viewModel.phoneViewModel
                    )
                }

                composable(
                    route = Screen.Verification.route,
                    enterTransition = {
                        when (initialState.destination.route) {
                            Screen.Phone.route -> slideInHorizontally { 1000 }
                            else -> EnterTransition.None
                        }
                    },
                    exitTransition = {
                        when (targetState.destination.route) {
                            Screen.Phone.route -> slideOutHorizontally { 1000 }
                            else -> ExitTransition.None
                        }
                    }
                ) {
                    VerificationCodeScreen(
                        viewModel = viewModel.verificationViewModel
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                text = R.string.tx_confirm.value,
                modifier = Modifier.width(300.dp),
                onClick = {
                    viewModel.onEvent(AuthEvent.Next(context as Activity))
                }
            )
        }

        TextSnackbar(
            hostState = hostState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
                .align(Alignment.BottomCenter),
            backgroundColor = MaterialTheme.colorScheme.surface,
            textColor = MaterialTheme.colorScheme.onSurface
        )

        TextSnackbar(
            hostState = errorHostState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
                .align(Alignment.BottomCenter),
            backgroundColor = MaterialTheme.colorScheme.error,
            textColor = MaterialTheme.colorScheme.onError
        )
    }
}