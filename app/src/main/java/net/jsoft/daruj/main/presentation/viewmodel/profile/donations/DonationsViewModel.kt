package net.jsoft.daruj.main.presentation.viewmodel.profile.donations

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.plusAssign
import net.jsoft.daruj.common.util.uiText
import net.jsoft.daruj.main.domain.model.Donation
import net.jsoft.daruj.main.domain.usecase.GetDonationsUseCase
import javax.inject.Inject

@HiltViewModel
class DonationsViewModel @Inject constructor(
    private val getDonations: GetDonationsUseCase
) : LoadingViewModel<DonationsEvent, DonationsTask>() {

    private val mDonations = mutableStateListOf<Donation>()
    val donations = mDonations as List<Donation>

    init {
        viewModelScope.registerExceptionHandler { e -> mTaskFlow += DonationsTask.ShowError(e.uiText) }
    }

    override fun onEvent(event: DonationsEvent) {
        when (event) {
            is DonationsEvent.Refresh -> loadDonations(true)
            is DonationsEvent.ReachedEnd -> loadDonations()
        }
    }

    private fun loadDonations(reset: Boolean = mDonations.isEmpty()) {
        if (reset) mDonations.clear()
        viewModelScope.loadSafely("LOAD_DONATIONS") { mDonations += getDonations(reset) }
    }
}