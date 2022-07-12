package net.jsoft.daruj.auth.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.jsoft.daruj.common.presentation.component.TextBox
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme

@Composable
fun VerificationCodeInputBox(
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    code: String = "",
    onCodeChange: (code: String) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until 6) {
            TextBox(
                modifier = Modifier
                    .width(40.dp)
                    .then(
                        if (i == code.length) {
                            Modifier.focusRequester(focusRequester)
                        } else {
                            Modifier
                        }
                    ),
                text = if (i < code.length) {
                    code[i].toString()
                } else {
                    ""
                },
                maxLength = 1,
                contentAlignment = Alignment.Center,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = if (i != 5) ImeAction.Next else ImeAction.Done
                ),
                onValueChange = { value ->
                    onCodeChange(code.substring(0, i) + value)

                    if (value.isEmpty()) {
                        if(i != 0) focusManager.moveFocus(FocusDirection.Previous)
                    } else if(i != 5) {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                },
                onCustomClick = {
                    focusRequester.requestFocus()
                }
            )
        }
    }
}

@Preview
@Composable
fun VerificationCodeInputBoxPreview() {
    DarujTheme {
        var code by remember { mutableStateOf("") }

        val focusRequester = remember { FocusRequester() }

        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            scope.launch {
                delay(2000)
                focusRequester.requestFocus()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            VerificationCodeInputBox(
                focusRequester,
                modifier = Modifier.width(300.dp),
                code = code,
                onCodeChange = { newCode ->
                    code = newCode
                }
            )
        }
    }
}