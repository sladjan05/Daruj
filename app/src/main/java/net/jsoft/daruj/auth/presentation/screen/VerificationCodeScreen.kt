package net.jsoft.daruj.auth.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.component.VerificationCodeInputBox
import net.jsoft.daruj.auth.presentation.viewmodel.AuthEvent
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeEvent
import net.jsoft.daruj.auth.presentation.viewmodel.verification.VerificationCodeViewModel
import net.jsoft.daruj.common.presentation.ui.theme.onBackgroundDim
import net.jsoft.daruj.common.util.value

@Composable
fun VerificationCodeScreen(
    viewModel: VerificationCodeViewModel,
    isLoading: Boolean,
    onEvent: (event: AuthEvent) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
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

        Text(
            text = R.string.tx_check_sms.value,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = R.string.tx_code_is_sent.value,
            modifier = Modifier.widthIn(max = 270.dp),
            color = MaterialTheme.colorScheme.onBackgroundDim,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(45.dp))

        VerificationCodeInputBox(
            focusRequester = focusRequester,
            modifier = Modifier
                .width(300.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = R.string.tx_no_code_question.value,
                color = MaterialTheme.colorScheme.onBackgroundDim,
                style = MaterialTheme.typography.bodyMedium
            )

            ClickableText(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = if (isLoading) {
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
                    onEvent(AuthEvent.SendVerificationCodeAgain)
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}