package net.jsoft.daruj.auth.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberEvent
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberViewModel
import net.jsoft.daruj.common.presentation.component.DropdownSelectionBox
import net.jsoft.daruj.common.presentation.component.TextBox
import net.jsoft.daruj.common.presentation.component.TitleSubtitle
import net.jsoft.daruj.common.util.MainTestTags
import net.jsoft.daruj.common.util.countriesSortedBySerbianAlphabet
import net.jsoft.daruj.common.util.indicationlessClickable
import net.jsoft.daruj.common.util.value

@Composable
fun PhoneNumberScreen(
    viewModel: PhoneNumberViewModel,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier
            .testTag(MainTestTags.Auth.PHONE_NUMBER_SCREEN)
            .fillMaxWidth()
            .wrapContentHeight()
            .indicationlessClickable {
                viewModel.onEvent(PhoneNumberEvent.Dismiss)
            },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleSubtitle(
            title = R.string.tx_enter_your_phone_number.value,
            subtitle = R.string.tx_enter_your_phone_number_desc.value
        )

        Spacer(modifier = Modifier.height(45.dp))

        val countries = LocalContext.current.countriesSortedBySerbianAlphabet()
        val countryNames = countries.map { country -> country.resId.value }

        DropdownSelectionBox(
            text = viewModel.country?.resId?.value ?: R.string.tx_unknown.value,
            modifier = Modifier
                .testTag(MainTestTags.Auth.COUNTRY_DROPDOWN)
                .width(300.dp),
            items = countryNames,
            expanded = viewModel.countryExpanded,
            enabled = !isLoading,
            onClick = {
                viewModel.onEvent(PhoneNumberEvent.CountryClick)
            },
            onSelected = { index ->
                viewModel.onEvent(PhoneNumberEvent.CountryChange(countries[index]))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.width(300.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextBox(
                modifier = Modifier
                    .testTag(MainTestTags.Auth.DIAL_CODE_TEXTBOX)
                    .width(IntrinsicSize.Min),
                text = viewModel.dialCode.value,
                prefix = "+",
                maxLength = 4,
                enabled = !isLoading,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                onValueChange = { value ->
                    if (value.isDigitsOnly()) {
                        viewModel.onEvent(PhoneNumberEvent.DialCodeChange(value))
                    }
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextBox(
                modifier = Modifier
                    .testTag(MainTestTags.Auth.PHONE_NUMBER_TEXTBOX)
                    .fillMaxWidth(),
                text = viewModel.phoneNumber.value,
                hint = R.string.tx_phone_number.value,
                maxLength = 11,
                enabled = !isLoading,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                onValueChange = { value ->
                    if (value.isDigitsOnly()) {
                        viewModel.onEvent(PhoneNumberEvent.PhoneNumberChange(value))
                    }
                }
            )
        }
    }
}