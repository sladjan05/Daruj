package net.jsoft.daruj.auth.presentation.screen

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberEvent
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberTask
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberViewModel
import net.jsoft.daruj.common.misc.MainTestTags
import net.jsoft.daruj.common.presentation.component.DropdownSelectionBox
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.common.presentation.component.TextBox
import net.jsoft.daruj.common.presentation.screen.ScreenWithSnackbars
import net.jsoft.daruj.common.presentation.screen.ScreenWithTitleAndSubtitle
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.utils.*
import net.jsoft.daruj.create_account.presentation.CreateAccountActivity
import net.jsoft.daruj.main.presentation.MainActivity

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun PhoneNumberScreen(
    viewModel: PhoneNumberViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    context as Activity

    val hostState = rememberSnackbarHostState()
    val errorHostState = rememberSnackbarHostState()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collect { task ->
            when (task) {
                is PhoneNumberTask.ShowVerificationScreen -> {
                    val fullPhoneNumber = viewModel.fullPhoneNumber.getValue(context)
                    val route = Screen.Verification.route(
                        Screen.Verification.FULL_PHONE_NUMBER to fullPhoneNumber
                    )

                    navController.navigate(route)
                }

                is PhoneNumberTask.ShowCreateAccountScreen -> context.switchActivity<CreateAccountActivity>()
                is PhoneNumberTask.ShowMainScreen -> context.switchActivity<MainActivity>()

                is PhoneNumberTask.ShowInfo -> hostState.showSnackbar(task.message.getValue(context))
                is PhoneNumberTask.ShowError -> errorHostState.showSnackbar(task.message.getValue(context))
            }
        }
    }

    LaunchedEffect(viewModel.isLoading) {
        if (viewModel.isLoading) {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    ScreenWithSnackbars(
        infoHostState = hostState,
        errorHostState = errorHostState,
        modifier = Modifier.fillMaxSize()
    ) {
        ScreenWithTitleAndSubtitle(
            title = R.string.tx_enter_your_phone_number.value,
            subtitle = R.string.tx_enter_your_phone_number_desc.value,
            modifier = Modifier
                .testTag(MainTestTags.Auth.PHONE_NUMBER_SCREEN)
                .fillMaxSize()
                .indicationlessClickable {
                    viewModel.onEvent(PhoneNumberEvent.Dismiss)
                }
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val countries = context.countriesSortedBySerbianAlphabet()
                val countryNames = countries.map { country -> country.resId.value }

                DropdownSelectionBox(
                    text = viewModel.country?.resId?.value ?: R.string.tx_unknown.value,
                    modifier = Modifier
                        .testTag(MainTestTags.Auth.COUNTRY_DROPDOWN)
                        .width(300.dp),
                    items = countryNames,
                    expanded = viewModel.countryExpanded,
                    enabled = !viewModel.isLoading,
                    onClick = {
                        viewModel.onEvent(PhoneNumberEvent.CountryClick)
                    },
                    onSelected = { index ->
                        viewModel.onEvent(PhoneNumberEvent.CountryChange(countries[index]))
                    }
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))

                Row(
                    modifier = Modifier.width(300.dp),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
                ) {
                    TextBox(
                        text = viewModel.dialCode.value,
                        onValueChange = { value ->
                            viewModel.onEvent(PhoneNumberEvent.DialCodeChange(value))
                        },
                        modifier = Modifier
                            .testTag(MainTestTags.Auth.DIAL_CODE_TEXTBOX)
                            .width(IntrinsicSize.Min),
                        prefix = "+",
                        maxLength = 4,
                        enabled = !viewModel.isLoading,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        )
                    )

                    TextBox(
                        text = viewModel.phoneNumber.value,
                        onValueChange = { value ->
                            viewModel.onEvent(PhoneNumberEvent.PhoneNumberChange(value))
                        },
                        modifier = Modifier
                            .testTag(MainTestTags.Auth.PHONE_NUMBER_TEXTBOX)
                            .fillMaxWidth(),
                        hint = R.string.tx_phone_number.value,
                        maxLength = 11,
                        enabled = !viewModel.isLoading,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        )
                    )
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

                PrimaryButton(
                    text = R.string.tx_confirm.value,
                    onClick = {
                        viewModel.onEvent(PhoneNumberEvent.SendVerificationCodeClick(context))
                    },
                    modifier = Modifier
                        .testTag(MainTestTags.Auth.NEXT_BUTTON)
                        .width(300.dp),
                    enabled = !viewModel.isLoading
                )
            }
        }
    }
}