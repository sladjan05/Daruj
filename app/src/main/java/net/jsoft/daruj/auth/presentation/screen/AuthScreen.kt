package net.jsoft.daruj.auth.presentation.screen

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.component.NumberKeyboard
import net.jsoft.daruj.auth.presentation.viewmodel.AuthEvent
import net.jsoft.daruj.auth.presentation.viewmodel.AuthTask
import net.jsoft.daruj.auth.presentation.viewmodel.AuthViewModel
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberViewModel
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeEvent
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeViewModel
import net.jsoft.daruj.common.presentation.component.ErrorInfoSnackbars
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.common.util.rememberSnackbarHostState
import net.jsoft.daruj.common.util.switchActivity
import net.jsoft.daruj.common.util.value
import net.jsoft.daruj.create_account.presentation.CreateAccountActivity

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    phoneViewModel: PhoneNumberViewModel = hiltViewModel(),
    verificationViewModel: VerificationCodeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val navController = rememberAnimatedNavController()

    val hostState = rememberSnackbarHostState()
    val errorHostState = rememberSnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is AuthTask.ShowVerificationScreen -> {
                    keyboardController?.hide()

                    navController.popBackStack()
                    navController.navigate(Screen.Verification.route)
                }

                is AuthTask.Finish -> context.switchActivity<CreateAccountActivity>()

                is AuthTask.ShowInfo -> hostState.showSnackbar(task.message.getValue(context))
                is AuthTask.ShowError -> errorHostState.showSnackbar(task.message.getValue(context))
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { verificationViewModel.code }.collectLatest { code ->
            val codeString = code.getValue(context)

            if (codeString.length == 6) {
                viewModel.onEvent(AuthEvent.VerifyWithCodeClick(codeString))
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(
            visible = (viewModel.screen == Screen.Verification) && (viewModel.waitTimeProgress != 0f),
            enter = slideInVertically { -it },
            exit = slideOutVertically { -it }
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
                    PhoneNumberScreen(
                        viewModel = phoneViewModel,
                        isLoading = viewModel.isLoading
                    )
                }

                composable(
                    route = Screen.Verification.route,
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
                    VerificationCodeScreen(
                        viewModel = verificationViewModel,
                        isLoading = viewModel.isLoading,
                        waitTime = viewModel.waitTime,
                        canResendVerificationCode = viewModel.waitTimeProgress == 0f,
                        resendVerificationCode = {
                            viewModel.onEvent(AuthEvent.ResendVerificationCodeClick)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            AnimatedVisibility(
                visible = viewModel.screen == Screen.Phone,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                PrimaryButton(
                    text = R.string.tx_confirm.value,
                    enabled = !viewModel.isLoading,
                    modifier = Modifier.width(300.dp),
                    onClick = {
                        viewModel.onEvent(
                            AuthEvent.SendVerificationCodeClick(
                                activity = context as Activity,
                                dialCode = phoneViewModel.dialCode.getValue(context),
                                phoneNumber = phoneViewModel.phoneNumber.getValue(context)
                            )
                        )
                    }
                )
            }
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Box {
                ErrorInfoSnackbars(
                    infoHostState = hostState,
                    errorHostState = errorHostState
                )
            }

            Box {
                NumberKeyboard(
                    visible = !viewModel.isLoading && (viewModel.screen == Screen.Verification),
                    onNumber = { number ->
                        verificationViewModel.onEvent(VerificationCodeEvent.NumberClick(number))
                    },
                    onDelete = {
                        verificationViewModel.onEvent(VerificationCodeEvent.DeleteClick)
                    }
                )
            }
        }
    }
}