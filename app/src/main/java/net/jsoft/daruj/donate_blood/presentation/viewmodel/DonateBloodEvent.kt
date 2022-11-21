package net.jsoft.daruj.donate_blood.presentation.viewmodel

import android.net.Uri

sealed interface DonateBloodEvent {
    data class PicturePick(val uri: Uri) : DonateBloodEvent
}