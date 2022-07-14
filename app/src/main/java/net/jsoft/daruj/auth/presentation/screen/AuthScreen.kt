package net.jsoft.daruj.auth.presentation.screen

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.viewmodel.AuthEvent
import net.jsoft.daruj.auth.presentation.viewmodel.AuthTask
import net.jsoft.daruj.auth.presentation.viewmodel.AuthViewModel
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberViewModel
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeViewModel
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.common.presentation.component.TextSnackbar
import net.jsoft.daruj.common.util.value

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    phoneViewModel: PhoneNumberViewModel = hiltViewModel(),
    verificationViewModel: VerificationCodeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val navController = rememberAnimatedNavController()

    val hostState = remember { SnackbarHostState() }
    val errorHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        LaunchedEffect(Unit) {
            viewModel.taskFlow.collectLatest { task ->
                when (task) {
                    is AuthTask.ShowVerificationScreen -> {
                        navController.popBackStack()
                        navController.navigate(Screen.Verification.route)
                    }
                    is AuthTask.Finish -> hostState.showSnackbar("Uspješna prijava") // TODO

                    is AuthTask.ShowInfo -> hostState.showSnackbar(task.message.getValue(context))
                    is AuthTask.ShowError -> errorHostState.showSnackbar(task.message.getValue(context))
                }
            }
        }

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
                    .heightIn(min = 260.dp),
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
                        viewModel = phoneViewModel,
                        isLoading = viewModel.isLoading
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
                        viewModel = verificationViewModel,
                        isLoading = viewModel.isLoading,
                        onEvent = viewModel::onEvent
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                text = R.string.tx_confirm.value,
                enabled = !viewModel.isLoading,
                modifier = Modifier.width(300.dp),
                onClick = {
                    when (viewModel.screen) {
                        is Screen.Phone -> viewModel.onEvent(
                            AuthEvent.SendVerificationCode(
                                activity = context as Activity,
                                dialCode = phoneViewModel.dialCode.getValue(context),
                                phoneNumber = phoneViewModel.phoneNumber.getValue(context)
                            )
                        )

                        is Screen.Verification -> viewModel.onEvent(AuthEvent.VerifyWithCode(verificationViewModel.code.getValue(context)))
                    }
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