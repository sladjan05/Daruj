package net.jsoft.daruj.auth.presentation.screen

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import net.jsoft.daruj.common.util.rememberSnackbarHostState
import net.jsoft.daruj.common.util.value
import net.jsoft.daruj.create_account.presentation.CreateAccountActivity

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    phoneViewModel: PhoneNumberViewModel = hiltViewModel(),
    verificationViewModel: VerificationCodeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val navController = rememberAnimatedNavController()

    val hostState = rememberSnackbarHostState()
    val errorHostState = rememberSnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is AuthTask.ShowVerificationScreen -> {
                    navController.popBackStack()
                    navController.navigate(Screen.Verification.route)
                }
                is AuthTask.Finish -> {
                    val intent = Intent(
                        context,
                        CreateAccountActivity::class.java
                    )

                    context.startActivity(intent)
                    (context as Activity).finish()
                }

                is AuthTask.ShowInfo -> hostState.showSnackbar(task.message.getValue(context))
                is AuthTask.ShowError -> errorHostState.showSnackbar(task.message.getValue(context))
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(
            visible = (viewModel.screen == Screen.Verification) && (viewModel.waitTimeProgress != 0f)
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surface,
                progress = viewModel.waitTimeProgress
            )
        }

        Column(
            modifier = Modifier.padding(top = 70.dp),
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
                        waitTime = viewModel.waitTime,
                        waitTimeProgress = viewModel.waitTimeProgress,
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