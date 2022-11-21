package net.jsoft.daruj.modify_post.presentation.viewmodel

import android.net.Uri
import net.jsoft.daruj.common.domain.model.Blood

sealed interface ModifyPostEvent {
    data class PictureChange(val uri: Uri) : ModifyPostEvent

    data class NameChange(val name: String) : ModifyPostEvent
    data class SurnameChange(val surname: String) : ModifyPostEvent
    data class ParentNameChange(val parentName: String) : ModifyPostEvent

    data class LocationChange(val location: String) : ModifyPostEvent

    data class DonorsRequiredChange(val donorsRequired: String) : ModifyPostEvent

    object BloodClick : ModifyPostEvent
    data class BloodChange(val blood: Blood) : ModifyPostEvent

    data class DescriptionChange(val description: String) : ModifyPostEvent

    object Agreement1Click : ModifyPostEvent
    object Agreement2Click : ModifyPostEvent

    object Dismiss : ModifyPostEvent
    object Finish : ModifyPostEvent
}