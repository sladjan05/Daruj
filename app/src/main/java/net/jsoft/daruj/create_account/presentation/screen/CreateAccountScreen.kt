package net.jsoft.daruj.create_account.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.component.*
import net.jsoft.daruj.common.presentation.ui.theme.onBackgroundDim
import net.jsoft.daruj.common.util.getBitmap
import net.jsoft.daruj.common.util.getValues
import net.jsoft.daruj.common.util.rememberSnackbarHostState
import net.jsoft.daruj.common.util.value
import net.jsoft.daruj.create_account.presentation.component.ProfilePicture
import net.jsoft.daruj.create_account.presentation.viewmodel.CreateAccountEvent
import net.jsoft.daruj.create_account.presentation.viewmodel.CreateAccountTask
import net.jsoft.daruj.create_account.presentation.viewmodel.CreateAccountViewModel

@Composable
fun CreateAccountScreen(
    viewModel: CreateAccountViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val hostState = rememberSnackbarHostState()
    val errorHostState = rememberSnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is CreateAccountTask.Finish -> {
                    hostState.showSnackbar("Napravljen nalog") // TODO
                }

                is CreateAccountTask.ShowError -> errorHostState.showSnackbar(task.message.getValue(context))
                is CreateAccountTask.ShowInfo -> hostState.showSnackbar(task.message.getValue(context))
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            TitleSubtitle(
                title = R.string.tx_enter_your_data.value,
                subtitle = R.string.tx_enter_your_data_description.value
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .widthIn(
                        min = 300.dp,
                        max = 300.dp
                    )
                    .heightIn(
                        min = 200.dp,
                        max = 200.dp
                    )
            ) {
                ProfilePicture(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.TopCenter),
                    bitmap = viewModel.pictureUri?.getBitmap(context),
                    pictureChangeEnabled = !viewModel.isLoading,
                    onPictureChange = { uri ->
                        viewModel.onEvent(CreateAccountEvent.PictureChange(uri))
                    }
                )

                SexSelectionBox(
                    sex = viewModel.sex,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 7.dp),
                    expanded = viewModel.sexExpanded,
                    enabled = !viewModel.isLoading,
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
                    enabled = !viewModel.isLoading,
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
                    enabled = !viewModel.isLoading,
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
                    enabled = !viewModel.isLoading,
                    maxLength = 20,
                    onValueChange = { value ->
                        viewModel.onEvent(CreateAccountEvent.SurnameChange(value))
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = R.string.tx_date_of_birth.value,
                    color = MaterialTheme.colorScheme.onBackgroundDim,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DropdownSelectionBox(
                        text = viewModel.birth.dayOfMonth.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        items = viewModel.days.value,
                        expanded = viewModel.dayExpanded,
                        enabled = !viewModel.isLoading,
                        onClick = {
                            viewModel.onEvent(CreateAccountEvent.DayClick)
                        },
                        onSelected = { index ->
                            viewModel.onEvent(CreateAccountEvent.DayIndexChange(index))
                        },
                        onDismiss = {
                            viewModel.onEvent(CreateAccountEvent.DayDismiss)
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    val months = remember {
                        listOf(
                            R.string.tx_jan,
                            R.string.tx_feb,
                            R.string.tx_mar,
                            R.string.tx_apr,
                            R.string.tx_may,
                            R.string.tx_jun,
                            R.string.tx_jul,
                            R.string.tx_aug,
                            R.string.tx_sep,
                            R.string.tx_okt,
                            R.string.tx_nov,
                            R.string.tx_dec
                        ).getValues(context)
                    }

                    DropdownSelectionBox(
                        text = months[viewModel.birth.month.ordinal],
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        items = months,
                        expanded = viewModel.monthExpanded,
                        enabled = !viewModel.isLoading,
                        onClick = {
                            viewModel.onEvent(CreateAccountEvent.MonthClick)
                        },
                        onSelected = { index ->
                            viewModel.onEvent(CreateAccountEvent.MonthIndexChange(index))
                        },
                        onDismiss = {
                            viewModel.onEvent(CreateAccountEvent.MonthDismiss)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                DropdownSelectionBox(
                    text = viewModel.birth.year.toString(),
                    modifier = Modifier.fillMaxWidth(),
                    items = CreateAccountViewModel.years.value,
                    expanded = viewModel.yearExpanded,
                    enabled = !viewModel.isLoading,
                    onClick = {
                        viewModel.onEvent(CreateAccountEvent.YearClick)
                    },
                    onSelected = { index ->
                        viewModel.onEvent(CreateAccountEvent.YearIndexChange(index))
                    },
                    onDismiss = {
                        viewModel.onEvent(CreateAccountEvent.YearDismiss)
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier.width(300.dp),
            ) {
                Text(
                    text = R.string.tx_legal_id_optional.value,
                    modifier = Modifier.align(Alignment.Start),
                    color = MaterialTheme.colorScheme.onBackgroundDim,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(2.dp))

                TextBox(
                    modifier = Modifier.fillMaxWidth(),
                    text = viewModel.legalId.value,
                    hint = R.string.tx_id_number.value,
                    enabled = !viewModel.isLoading,
                    onValueChange = { value ->
                        viewModel.onEvent(CreateAccountEvent.LegalIdChange(value))
                    }
                )
            }

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
                modifier = Modifier.widthIn(max = 260.dp),
                color = MaterialTheme.colorScheme.onBackgroundDim,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))
        }

        ErrorInfoSnackbars(
            infoHostState = hostState,
            errorHostState = errorHostState
        )
    }
}