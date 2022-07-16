package net.jsoft.daruj.create_account.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.component.*
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme
import net.jsoft.daruj.common.presentation.ui.theme.onBackgroundDim
import net.jsoft.daruj.common.util.value
import net.jsoft.daruj.create_account.presentation.component.ProfilePicture
import net.jsoft.daruj.create_account.presentation.viewmodel.CreateAccountEvent
import net.jsoft.daruj.create_account.presentation.viewmodel.CreateAccountViewModel

@Composable
fun CreateAccountScreen(
    viewModel: CreateAccountViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.padding(top = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleSubtitle(
                title = R.string.tx_enter_your_data.value,
                subtitle = R.string.tx_enter_your_data_description.value
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .heightIn(
                        min = 200.dp,
                        max = 200.dp
                    )
                    .widthIn(
                        min = 300.dp,
                        max = 300.dp
                    )
            ) {
                ProfilePicture(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.TopCenter),
                    pictureChangeEnabled = true
                )

                SexSelectionBox(
                    sex = viewModel.sex,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 7.dp),
                    expanded = viewModel.sexExpanded,
                    onClick = {
                        viewModel.onEvent(CreateAccountEvent.SexClick)
                    },
                    onSelected = { sex ->
                        viewModel.onEvent(CreateAccountEvent.SexChange(sex))
                    }
                )

                BloodSelectionBox(
                    blood = viewModel.blood,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    expanded = viewModel.bloodExpanded,
                    onClick = {
                        viewModel.onEvent(CreateAccountEvent.BloodClick)
                    },
                    onSelected = { blood ->
                        viewModel.onEvent(CreateAccountEvent.BloodChange(blood))
                    }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier.width(300.dp),
            ) {
                Text(
                    text = R.string.tx_primary_data.value,
                    color = MaterialTheme.colorScheme.onBackgroundDim,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(2.dp))

                TextBox(
                    modifier = Modifier.fillMaxWidth(),
                    text = viewModel.name.value,
                    hint = R.string.tx_name.value,
                    maxLength = 15,
                    onValueChange = { value ->
                        viewModel.onEvent(CreateAccountEvent.NameChange(value))
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextBox(
                    modifier = Modifier.fillMaxWidth(),
                    text = viewModel.surname.value,
                    hint = R.string.tx_surname.value,
                    maxLength = 20,
                    onValueChange = { value ->
                        viewModel.onEvent(CreateAccountEvent.SurnameChange(value))
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier.width(300.dp),
            ) {
                Text(
                    text = R.string.tx_primary_data.value,
                    color = MaterialTheme.colorScheme.onBackgroundDim,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DropdownSelectionBox(
                        text = viewModel.birth.dayOfMonth.toString(),
                        modifier = Modifier.width(90.dp),
                        items = viewModel.days.value,
                        expanded = viewModel.dayExpanded,
                        onClick = {
                            viewModel.onEvent(CreateAccountEvent.DayClick)
                        },
                        onSelected = { index ->
                            viewModel.onEvent(CreateAccountEvent.DayIndexChange(index))
                        }
                    )

                    val months = listOf(
                        R.string.tx_jan.value,
                        R.string.tx_feb.value,
                        R.string.tx_mar.value,
                        R.string.tx_apr.value,
                        R.string.tx_maj.value,
                        R.string.tx_jun.value,
                        R.string.tx_jul.value,
                        R.string.tx_avg.value,
                        R.string.tx_sep.value,
                        R.string.tx_okt.value,
                        R.string.tx_nov.value,
                        R.string.tx_dec.value
                    )

                    DropdownSelectionBox(
                        text = months[viewModel.birth.month.ordinal],
                        modifier = Modifier.width(90.dp),
                        items = months,
                        expanded = viewModel.monthExpanded,
                        onClick = {
                            viewModel.onEvent(CreateAccountEvent.MonthClick)
                        },
                        onSelected = { index ->
                            viewModel.onEvent(CreateAccountEvent.MonthIndexChange(index))
                        }
                    )

                    DropdownSelectionBox(
                        text = viewModel.birth.year.toString(),
                        modifier = Modifier.width(90.dp),
                        items = CreateAccountViewModel.years.value,
                        expanded = viewModel.yearExpanded,
                        onClick = {
                            viewModel.onEvent(CreateAccountEvent.YearClick)
                        },
                        onSelected = { index ->
                            viewModel.onEvent(CreateAccountEvent.YearIndexChange(index))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = R.string.tx_legal_id_optional.value,
                color = MaterialTheme.colorScheme.onBackgroundDim,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(2.dp))

            TextBox(
                modifier = Modifier.width(300.dp),
                text = viewModel.legalId.value,
                hint = R.string.tx_id_number.value,
                onValueChange = { value ->
                    viewModel.onEvent(CreateAccountEvent.LegalIdChange(value))
                }
            )

            Spacer(modifier = Modifier.height(60.dp))

            PrimaryButton(
                text = R.string.tx_create_account.value,
                enabled = !viewModel.isLoading,
                modifier = Modifier.width(300.dp),
                onClick = {
                    viewModel.onEvent(CreateAccountEvent.CreateAccount)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = R.string.tx_after_this_step_you_will_become_a_memeber.value,
                modifier = Modifier.widthIn(max = 280.dp),
                color = MaterialTheme.colorScheme.onBackgroundDim,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Preview
@Composable
fun CreateAccountScreenPreview() {
    DarujTheme {
        CreateAccountScreen()
    }
}