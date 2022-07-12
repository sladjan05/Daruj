package net.jsoft.daruj.auth.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import net.jsoft.daruj.R
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberEvent
import net.jsoft.daruj.auth.presentation.viewmodel.phone.PhoneNumberViewModel
import net.jsoft.daruj.common.presentation.component.DropdownSelectionBox
import net.jsoft.daruj.common.presentation.component.TextBox
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme
import net.jsoft.daruj.common.presentation.ui.theme.onBackgroundDim
import net.jsoft.daruj.common.util.countriesSortedBySerbianAlphabet
import net.jsoft.daruj.common.util.value

@Composable
fun PhoneNumberScreen(
    viewModel: PhoneNumberViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = R.string.tx_enter_your_phone_number.value,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = R.string.tx_enter_your_phone_number_desc.value,
            modifier = Modifier.widthIn(max = 270.dp),
            color = MaterialTheme.colorScheme.onBackgroundDim,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(45.dp))

        val countries = LocalContext.current.countriesSortedBySerbianAlphabet()
        val countryNames = countries.map { country -> country.resId.value }

        DropdownSelectionBox(
            text = viewModel.country?.resId?.value ?: R.string.tx_unknown.value,
            modifier = Modifier.width(300.dp),
            items = countryNames,
            expanded = viewModel.countryDropdownExpanded,
            onClick = {
                viewModel.onEvent(PhoneNumberEvent.ExpandCountryDropdown)
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
                modifier = Modifier.width(IntrinsicSize.Min),
                text = viewModel.dialCode.value,
                prefix = "+",
                maxLength = 4,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                onValueChange = { value ->
                    viewModel.onEvent(PhoneNumberEvent.DialCodeChange(value))
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextBox(
                modifier = Modifier.fillMaxWidth(),
                text = viewModel.phoneNumber.value,
                hint = R.string.tx_phone_number.value,
                maxLength = 10,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                onValueChange = { value ->
                    viewModel.onEvent(PhoneNumberEvent.PhoneNumberChange(value))
                }
            )
        }
    }
}