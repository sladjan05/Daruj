package net.jsoft.daruj.main.presentation.screen

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import net.jsoft.daruj.R
import net.jsoft.daruj.common.util.value
import net.jsoft.daruj.main.presentation.component.DonationLazyColumn
import net.jsoft.daruj.main.presentation.viewmodel.profile.donations.DonationsEvent
import net.jsoft.daruj.main.presentation.viewmodel.profile.donations.DonationsViewModel

@Composable
fun DonationsScreen(
    navController: NavController,
    viewModel: DonationsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    DonationLazyColumn(
        donations = viewModel.donations,
        isLoading = viewModel.isLoading,
        noContentText = R.string.tx_no_donations.value,
        onRefresh = { viewModel.onEvent(DonationsEvent.Refresh) },
        onEndReached = { viewModel.onEvent(DonationsEvent.ReachedEnd) },
        onPostClick = { donation ->
            val route = Screen.DetailedPost.route(
                Screen.DetailedPost.POST_ID to donation.post.data.id
            )

            navController.navigate(route)
        },
        modifier = Modifier.fillMaxSize()
    )
}