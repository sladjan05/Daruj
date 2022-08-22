package net.jsoft.daruj.modify_post.presentation.viewmodel

import android.net.Uri
import net.jsoft.daruj.common.domain.model.Blood

sealed class ModifyPostEvent {
    class PictureChange(val uri: Uri) : ModifyPostEvent()

    class NameChange(val name: String) : ModifyPostEvent()
    class SurnameChange(val surname: String) : ModifyPostEvent()
    class ParentNameChange(val parentName: String) : ModifyPostEvent()

    class LocationChange(val location: String) : ModifyPostEvent()

    class DonorsRequiredChange(val donorsRequired: String) : ModifyPostEvent()

    object BloodClick : ModifyPostEvent()
    class BloodChange(val blood: Blood) : ModifyPostEvent()

    class DescriptionChange(val description: String) : ModifyPostEvent()

    object Agreement1Click : ModifyPostEvent()
    object Agreement2Click : ModifyPostEvent()

    object Dismiss : ModifyPostEvent()
    object Finish : ModifyPostEvent()
}