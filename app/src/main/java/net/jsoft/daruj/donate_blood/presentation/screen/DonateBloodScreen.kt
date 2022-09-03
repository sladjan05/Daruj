package net.jsoft.daruj.donate_blood.presentation.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import net.jsoft.daruj.R
import net.jsoft.daruj.common.presentation.component.PrimaryButton
import net.jsoft.daruj.common.presentation.screen.ScreenWithBackAndTitle
import net.jsoft.daruj.common.presentation.screen.ScreenWithSnackbars
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.utils.getValue
import net.jsoft.daruj.common.utils.rememberSnackbarHostState
import net.jsoft.daruj.common.utils.value
import net.jsoft.daruj.donate_blood.presentation.viewmodel.DonateBloodTask
import net.jsoft.daruj.donate_blood.presentation.viewmodel.DonateBloodViewModel

@Preview
@Composable
fun DonateBloodScreen(
    viewModel: DonateBloodViewModel = hiltViewModel()
) = DarujTheme {
    val context = LocalContext.current
    context as Activity

    val hostState = rememberSnackbarHostState()
    val errorHostState = rememberSnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is DonateBloodTask.ShowError -> errorHostState.showSnackbar(task.message.getValue(context))
            }
        }
    }

    ScreenWithSnackbars(
        infoHostState = hostState,
        errorHostState = errorHostState,
        modifier = Modifier.fillMaxSize(),
    ) {
        ScreenWithBackAndTitle(
            title = R.string.tx_thank_you_for_donation.value,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.mShapes.medium
                        )
                        .padding(MaterialTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                ) {
                    Text(
                        text = "${R.string.tx_donate_blood_step_1.value}:",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.mShapes.medium
                            )
                            .padding(MaterialTheme.spacing.small),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                    ) {
                        val mutable = viewModel.post?.mutable
                        val data = remember {
                            arrayOf(
                                R.string.tx_recipient.getValue(context) to mutable?.fullName,
                                R.string.tx_parent_name.getValue(context) to mutable?.parentName,
                                R.string.tx_located_at.getValue(context) to mutable?.location
                            )
                        }

                        data.forEach { (title, description) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${title}:",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.bodySmall
                                )

                                Text(
                                    text = description ?: "",
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = MaterialTheme.mShapes.medium
                        )
                        .padding(MaterialTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                ) {
                    Text(
                        text = "${R.string.tx_donate_blood_step_2.value}:",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    PrimaryButton(
                        text = R.string.tx_take_donation_receipt_picture.value,
                        onClick = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !viewModel.isLoading,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_camera),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        },
                        color = Color.White,
                        textColor = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}