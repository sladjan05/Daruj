package net.jsoft.daruj.create_account.presentation.screen

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.MainTestTags
import net.jsoft.daruj.common.presentation.component.*
import net.jsoft.daruj.common.presentation.screen.ScreenWithBackAndTitle
import net.jsoft.daruj.common.presentation.screen.ScreenWithSnackbars
import net.jsoft.daruj.common.presentation.screen.ScreenWithTitleAndSubtitle
import net.jsoft.daruj.common.presentation.ui.theme.bodySmaller
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.util.*
import net.jsoft.daruj.create_account.presentation.ModifyAccountActivity
import net.jsoft.daruj.create_account.presentation.component.LabeledSection
import net.jsoft.daruj.create_account.presentation.viewmodel.CreateAccountEvent
import net.jsoft.daruj.create_account.presentation.viewmodel.CreateAccountTask
import net.jsoft.daruj.create_account.presentation.viewmodel.ModifyAccountViewModel
import net.jsoft.daruj.main.presentation.MainActivity

@Composable
fun ModifyAccountScreen(
    intention: ModifyAccountActivity.Intention,
    viewModel: ModifyAccountViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    val errorHostState = rememberSnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is CreateAccountTask.CreateAccountClick -> when (intention) {
                    ModifyAccountActivity.Intention.CreateAccount -> context.switchActivity<MainActivity>()
                    ModifyAccountActivity.Intention.EditAccount -> context.goBack()
                }

                is CreateAccountTask.ShowError -> errorHostState.showSnackbar(task.message.getValue(context))
            }
        }
    }

    val content: @Composable BoxScope.() -> Unit = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.extraLarge)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
            ) {
                LocalProfilePicture(
                    pictureUri = viewModel.pictureUri,
                    onPictureChange = { uri -> viewModel.onEvent(CreateAccountEvent.PictureChange(uri)) },
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.TopCenter),
                    pictureChangeEnabled = !viewModel.isLoading
                )

                Row(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.spacedBy(150.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    SexSelectionBox(
                        selectedSex = viewModel.sex,
                        onClick = { viewModel.onEvent(CreateAccountEvent.SexClick) },
                        onSelected = { sex -> viewModel.onEvent(CreateAccountEvent.SexChange(sex)) },
                        expanded = viewModel.sexExpanded,
                        enabled = !viewModel.isLoading
                    )

                    BloodSelectionBox(
                        selectedBlood = viewModel.blood,
                        onClick = { viewModel.onEvent(CreateAccountEvent.BloodClick) },
                        onSelected = { blood -> viewModel.onEvent(CreateAccountEvent.BloodChange(blood)) },
                        expanded = viewModel.bloodExpanded,
                        enabled = !viewModel.isLoading
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))

            Column(
                modifier = Modifier.width(300.dp),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
            ) {
                LabeledSection(label = R.string.tx_primary_data.value) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
                    ) {
                        TextBox(
                            text = viewModel.name.value,
                            onValueChange = { name -> viewModel.onEvent(CreateAccountEvent.NameChange(name)) },
                            modifier = Modifier
                                .testTag(MainTestTags.CreateAccount.NAME_TEXTBOX)
                                .fillMaxWidth(),
                            hint = R.string.tx_name.value,
                            enabled = !viewModel.isLoading,
                            maxLength = 15,
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                        )

                        TextBox(
                            text = viewModel.surname.value,
                            onValueChange = { surname -> viewModel.onEvent(CreateAccountEvent.SurnameChange(surname)) },
                            modifier = Modifier
                                .testTag(MainTestTags.CreateAccount.SURNAME_TEXTBOX)
                                .fillMaxWidth(),
                            hint = R.string.tx_surname.value,
                            enabled = !viewModel.isLoading,
                            maxLength = 20,
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                        )
                    }
                }

                LabeledSection(label = R.string.tx_other_data.value) {
                    Column(
                        modifier = Modifier.padding(top = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
                    ) {
                        CheckBox(
                            text = R.string.tx_private_account.value,
                            checked = viewModel.isPrivate,
                            onCheck = { viewModel.onEvent(CreateAccountEvent.IsPrivateClick) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.huge))

            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryButton(
                    text = when (intention) {
                        ModifyAccountActivity.Intention.CreateAccount -> R.string.tx_create_account.value
                        ModifyAccountActivity.Intention.EditAccount -> R.string.tx_save.value
                    },
                    onClick = { viewModel.onEvent(CreateAccountEvent.Finish) },
                    modifier = Modifier
                        .testTag(MainTestTags.CreateAccount.CREATE_ACCOUNT_BUTTON)
                        .width(300.dp),
                    enabled = !viewModel.isLoading
                )

                if (intention == ModifyAccountActivity.Intention.CreateAccount) {
                    Text(
                        text = R.string.tx_after_this_step_you_will_become_a_memeber.value,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmaller,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    ScreenWithSnackbars(
        modifier = Modifier
            .fillMaxSize()
            .indicationlessClickable { viewModel.onEvent(CreateAccountEvent.Dismiss) }
            .verticalScroll(rememberScrollState()),
        errorHostState = errorHostState
    ) {
        when (intention) {
            ModifyAccountActivity.Intention.CreateAccount -> {
                ScreenWithTitleAndSubtitle(
                    title = R.string.tx_enter_your_data.value,
                    subtitle = R.string.tx_enter_your_data_description.value,
                    modifier = Modifier.fillMaxSize(),
                    content = content
                )
            }

            ModifyAccountActivity.Intention.EditAccount -> {
                ScreenWithBackAndTitle(
                    title = R.string.tx_settings.value,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(top = MaterialTheme.spacing.large),
                    content = content
                )
            }
        }
    }
}