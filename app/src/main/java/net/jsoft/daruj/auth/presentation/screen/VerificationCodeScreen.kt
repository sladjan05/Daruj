package net.jsoft.daruj.auth.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.component.VerificationCodeBox
import net.jsoft.daruj.auth.presentation.viewmodel.AuthEvent
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeEvent
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeViewModel
import net.jsoft.daruj.common.presentation.component.TitleSubtitle
import net.jsoft.daruj.common.presentation.ui.theme.onBackgroundDim
import net.jsoft.daruj.common.util.formatSMSWaitTime
import net.jsoft.daruj.common.util.rememberFocusRequester
import net.jsoft.daruj.common.util.rememberMutableInteractionSource
import net.jsoft.daruj.common.util.value

@Composable
fun VerificationCodeScreen(
    viewModel: VerificationCodeViewModel,
    isLoading: Boolean,
    waitTime: Int,
    waitTimeProgress: Float,
    onEvent: (event: AuthEvent) -> Unit
) {
    val focusRequester = rememberFocusRequester()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(isLoading) {
            if (isLoading) {
                focusManager.clearFocus()
            } else {
                focusRequester.requestFocus()
            }
        }

        TitleSubtitle(
            title = R.string.tx_check_sms.value,
            subtitle = R.string.tx_code_is_sent.value
        )

        Spacer(modifier = Modifier.height(45.dp))

        VerificationCodeBox(
            focusRequester = focusRequester,
            modifier = Modifier
                .width(300.dp)
                .clickable(
                    interactionSource = rememberMutableInteractionSource(),
                    indication = null
                ) {
                    focusRequester.requestFocus()
                },
            code = viewModel.code.value,
            enabled = !isLoading,
            onCodeChange = { code ->
                viewModel.onEvent(VerificationCodeEvent.CodeChange(code))
            }
        )

        Spacer(modifier = Modifier.height(26.dp))

        Row(
            modifier = Modifier.width(300.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = R.string.tx_no_code_question.value,
                color = MaterialTheme.colorScheme.onBackgroundDim,
                style = MaterialTheme.typography.bodyMedium
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = if (isLoading || (waitTimeProgress != 0f)) {
                                    MaterialTheme.colorScheme.onBackgroundDim
                                } else {
                                    MaterialTheme.colorScheme.onBackground
                                },
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append(R.string.tx_send_again.value)
                        }
                    },
                    onClick = {
                        if (!isLoading && (waitTimeProgress == 0f)) {
                            onEvent(AuthEvent.SendVerificationCodeAgainClick)
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium
                )

                AnimatedVisibility(visible = waitTimeProgress != 0f) {
                    Text(
                        text = formatSMSWaitTime(waitTime),
                        color = MaterialTheme.colorScheme.onBackgroundDim,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}