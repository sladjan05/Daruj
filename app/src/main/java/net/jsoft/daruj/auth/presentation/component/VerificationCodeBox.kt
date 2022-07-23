package net.jsoft.daruj.auth.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.jsoft.daruj.common.presentation.component.TextBox
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme
import net.jsoft.daruj.common.util.thenIf
import java.lang.Integer.min

private val WIDTH = 40.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VerificationCodeBox(
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    code: String = "",
    enabled: Boolean = true,
    onCodeChange: (code: String) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until 6) {
            TextBox(
                modifier = Modifier
                    .width(WIDTH)
                    .thenIf(
                        statement = i == min(code.length, 5),
                        modifier = Modifier.focusRequester(focusRequester)
                    ),
                text = if (i < code.length) code[i].toString() else "",
                maxLength = 1,
                enabled = enabled,
                contentAlignment = Alignment.Center,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = if (i == 5) ImeAction.Done else ImeAction.Next
                ),
                onValueChange = { value ->
                    if(!value.isDigitsOnly()) return@TextBox

                    onCodeChange(code.substring(0, i) + value)

                    if (value.isEmpty()) {
                        if (i != 0) focusManager.moveFocus(FocusDirection.Previous)
                    } else if (i != 5) {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                },
                onCustomClick = {
                    focusRequester.requestFocus()
                    keyboardController?.show()
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
            VerificationCodeBox(
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