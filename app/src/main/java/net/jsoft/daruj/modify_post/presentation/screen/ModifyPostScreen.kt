package net.jsoft.daruj.modify_post.presentation.screen

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.MainTestTags
import net.jsoft.daruj.common.presentation.component.BloodSelectionBox
import net.jsoft.daruj.common.presentation.component.CheckBox
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.common.presentation.component.TextBox
import net.jsoft.daruj.common.presentation.screen.ScreenWithBackAndTitle
import net.jsoft.daruj.common.presentation.screen.ScreenWithSnackbars
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.util.*
import net.jsoft.daruj.create_account.presentation.component.LabeledSection
import net.jsoft.daruj.modify_post.presentation.ModifyPostActivity
import net.jsoft.daruj.modify_post.presentation.component.PostPicture
import net.jsoft.daruj.modify_post.presentation.viewmodel.ModifyPostEvent
import net.jsoft.daruj.modify_post.presentation.viewmodel.ModifyPostTask
import net.jsoft.daruj.modify_post.presentation.viewmodel.ModifyPostViewModel

@Composable
fun ModifyPostScreen(
    intention: ModifyPostActivity.Intention,
    viewModel: ModifyPostViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    val errorHostState = rememberSnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is ModifyPostTask.Close -> context.goBack()
                is ModifyPostTask.ShowError -> errorHostState.showSnackbar(task.message.getValue(context))
            }
        }
    }

    ScreenWithSnackbars(
        modifier = Modifier
            .fillMaxSize()
            .indicationlessClickable { viewModel.onEvent(ModifyPostEvent.Dismiss) },
        errorHostState = errorHostState
    ) {
        ScreenWithBackAndTitle(
            title = when (intention) {
                is ModifyPostActivity.Intention.CreatePost -> R.string.tx_create_new_post.value
                is ModifyPostActivity.Intention.EditPost -> R.string.tx_edit_post.value
            },
            modifier = Modifier.fillMaxSize(),
            enabled = !viewModel.isLoading
        ) {
            NoOverscrollEffect {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .requiredWidth(300.dp)
                        .padding(
                            top = MaterialTheme.spacing.medium,
                            bottom = MaterialTheme.spacing.extraLarge
                        )
                        .align(Alignment.TopCenter),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
                ) {
                    LabeledSection(label = R.string.tx_picture.value) {
                        PostPicture(
                            pictureUri = viewModel.pictureUri,
                            onPictureChange = { uri -> viewModel.onEvent(ModifyPostEvent.PictureChange(uri)) },
                            modifier = Modifier
                                .size(150.dp)
                                .align(Alignment.Center),
                            canChangePicture = !viewModel.isLoading
                        )
                    }

                    LabeledSection(label = R.string.tx_primary_data.value) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            TextBox(
                                text = viewModel.name.value,
                                hint = R.string.tx_name.value,
                                onValueChange = { name -> viewModel.onEvent(ModifyPostEvent.NameChange(name)) },
                                modifier = Modifier.fillMaxWidth(),
                                maxLength = 15,
                                enabled = !viewModel.isLoading,
                                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                            )

                            TextBox(
                                text = viewModel.surname.value,
                                hint = R.string.tx_surname.value,
                                onValueChange = { surname -> viewModel.onEvent(ModifyPostEvent.SurnameChange(surname)) },
                                modifier = Modifier.fillMaxWidth(),
                                maxLength = 20,
                                enabled = !viewModel.isLoading,
                                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                            )

                            TextBox(
                                text = viewModel.parentName.value,
                                hint = R.string.tx_parent_name.value,
                                onValueChange = { parentName -> viewModel.onEvent(ModifyPostEvent.ParentNameChange(parentName)) },
                                modifier = Modifier.fillMaxWidth(),
                                maxLength = 15,
                                enabled = !viewModel.isLoading,
                                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                            )
                        }
                    }

                    LabeledSection(label = R.string.tx_other_data.value) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            TextBox(
                                text = viewModel.location.value,
                                hint = R.string.tx_located_at.value + "…",
                                onValueChange = { location -> viewModel.onEvent(ModifyPostEvent.LocationChange(location)) },
                                modifier = Modifier.fillMaxWidth(),
                                maxLength = 20,
                                enabled = !viewModel.isLoading,
                                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextBox(
                                    text = viewModel.donorsRequired.value,
                                    hint = R.string.tx_donor_count.value,
                                    onValueChange = { donorsRequired -> viewModel.onEvent(ModifyPostEvent.DonorsRequiredChange(donorsRequired)) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .offset(y = (-9).dp),
                                    enabled = !viewModel.isLoading,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )

                                BloodSelectionBox(
                                    selectedBlood = viewModel.blood,
                                    onClick = { viewModel.onEvent(ModifyPostEvent.BloodClick) },
                                    onSelected = { blood -> viewModel.onEvent(ModifyPostEvent.BloodChange(blood)) },
                                    expanded = viewModel.bloodExpanded,
                                    enabled = !viewModel.isLoading
                                )
                            }

                            TextBox(
                                text = viewModel.description.value,
                                hint = R.string.tx_short_description.value,
                                onValueChange = { description -> viewModel.onEvent(ModifyPostEvent.DescriptionChange(description)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                maxLength = 300,
                                multiline = true,
                                enabled = !viewModel.isLoading,
                                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        CheckBox(
                            text = R.string.tx_create_post_agreement_1.value,
                            checked = viewModel.agreement1Checked,
                            onCheck = { viewModel.onEvent(ModifyPostEvent.Agreement1Click) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        CheckBox(
                            text = R.string.tx_create_post_agreement_2.value,
                            checked = viewModel.agreement2Checked,
                            onCheck = { viewModel.onEvent(ModifyPostEvent.Agreement2Click) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    PrimaryButton(
                        text = when (intention) {
                            is ModifyPostActivity.Intention.CreatePost -> R.string.tx_create_post.value
                            is ModifyPostActivity.Intention.EditPost -> R.string.tx_save.value
                        },
                        onClick = { viewModel.onEvent(ModifyPostEvent.Finish) },
                        modifier = Modifier
                            .testTag(MainTestTags.CreateAccount.CREATE_ACCOUNT_BUTTON)
                            .fillMaxWidth(),
                        enabled = !viewModel.isLoading && viewModel.agreement1Checked && viewModel.agreement2Checked
                    )
                }
            }
        }
    }
}