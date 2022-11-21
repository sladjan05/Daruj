package net.jsoft.daruj.auth.presentation.screen

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.component.NumberKeyboard
import net.jsoft.daruj.auth.presentation.component.VerificationCodeBox
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeEvent
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeTask
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeViewModel
import net.jsoft.daruj.common.misc.MainTestTags
import net.jsoft.daruj.common.presentation.component.ErrorInfoSnackbars
import net.jsoft.daruj.common.presentation.screen.ScreenWithTitleAndSubtitle
import net.jsoft.daruj.common.presentation.ui.theme.onBackgroundDim
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.util.formatSMSWaitTime
import net.jsoft.daruj.common.util.rememberSnackbarHostState
import net.jsoft.daruj.common.util.switchActivity
import net.jsoft.daruj.common.util.value
import net.jsoft.daruj.create_account.presentation.ModifyAccountActivity
import net.jsoft.daruj.main.presentation.MainActivity

@Composable
fun VerificationCodeScreen(
    viewModel: VerificationCodeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    val hostState = rememberSnackbarHostState()
    val errorHostState = rememberSnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is VerificationCodeTask.ShowCreateAccountScreen -> context.switchActivity<ModifyAccountActivity>(
                    ModifyAccountActivity.Intention to ModifyAccountActivity.Intention.CreateAccount
                )

                is VerificationCodeTask.ShowMainScreen -> context.switchActivity<MainActivity>()

                is VerificationCodeTask.ShowInfo -> hostState.showSnackbar(task.message.getValue(context))
                is VerificationCodeTask.ShowError -> errorHostState.showSnackbar(task.message.getValue(context))
            }
        }
    }

    ScreenWithTitleAndSubtitle(
        title = R.string.tx_check_sms.value,
        subtitle = R.string.tx_check_sms_desc.value,
        modifier = Modifier
            .testTag(MainTestTags.Auth.VERIFICATION_SCREEN)
            .fillMaxSize(),
        screenBox = {
            AnimatedVisibility(
                visible = viewModel.waitTimeProgress != 0f,
                enter = slideInVertically { -it },
                exit = slideOutVertically { -it }
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .testTag(MainTestTags.Auth.WAIT_TIME_PROGRESS_BAR)
                        .fillMaxWidth(),
                    trackColor = MaterialTheme.colorScheme.surface,
                    progress = viewModel.waitTimeProgress
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VerificationCodeBox(
                code = viewModel.code.value,
                modifier = Modifier
                    .testTag(MainTestTags.Auth.VERIFICATION_CODE_BOX)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = R.string.tx_no_code_question.value,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ClickableText(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    color = if (viewModel.isLoading || viewModel.waitTimeProgress != 0f) {
                                        MaterialTheme.colorScheme.onBackground
                                    } else {
                                        MaterialTheme.colorScheme.onBackgroundDim
                                    },
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append(R.string.tx_send_again.value)
                            }
                        },
                        onClick = {
                            if (!viewModel.isLoading && viewModel.waitTimeProgress == 0f) {
                                viewModel.onEvent(VerificationCodeEvent.ResendVerificationCodeClick)
                            }
                        },
                        style = MaterialTheme.typography.bodySmall
                    )

                    AnimatedVisibility(visible = viewModel.waitTimeProgress != 0f) {
                        Text(
                            text = formatSMSWaitTime(viewModel.waitTime).value,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ErrorInfoSnackbars(
                infoHostState = hostState,
                errorHostState = errorHostState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )

            NumberKeyboard(
                visible = !viewModel.isLoading,
                onNumberClick = { number -> viewModel.onEvent(VerificationCodeEvent.NumberClick(number)) },
                onDeleteClick = { viewModel.onEvent(VerificationCodeEvent.DeleteClick) }
            )
        }
    }
}