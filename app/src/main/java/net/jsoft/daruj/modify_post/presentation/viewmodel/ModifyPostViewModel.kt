package net.jsoft.daruj.modify_post.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.misc.asUiText
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.utils.plusAssign
import net.jsoft.daruj.common.utils.uiText
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.modify_post.domain.usecase.CreatePostUseCase
import net.jsoft.daruj.modify_post.domain.usecase.UpdatePostPictureUseCase
import net.jsoft.daruj.modify_post.domain.usecase.UpdatePostUseCase
import net.jsoft.daruj.modify_post.presentation.ModifyPostActivity
import javax.inject.Inject

@HiltViewModel
class ModifyPostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

    private val createPost: CreatePostUseCase,
    private val updatePost: UpdatePostUseCase,
    private val updatePostPicture: UpdatePostPictureUseCase
) : LoadingViewModel<ModifyPostEvent, ModifyPostTask>() {

    var pictureUri by mutableStateOf<Uri?>(null)
        private set

    var name by mutableStateOf<UiText>("".asUiText())
        private set

    var surname by mutableStateOf<UiText>("".asUiText())
        private set

    var parentName by mutableStateOf<UiText>("".asUiText())
        private set

    var location by mutableStateOf<UiText>("".asUiText())
        private set

    var donorsRequired by mutableStateOf("".asUiText())
        private set

    var bloodExpanded by mutableStateOf(false)
        private set

    var blood by mutableStateOf(Blood.fromString("A+"))
        private set

    var description by mutableStateOf<UiText>("".asUiText())
        private set

    var agreement1Checked by mutableStateOf(false)
        private set

    var agreement2Checked by mutableStateOf(false)
        private set

    private val purpose = savedStateHandle.get<ModifyPostActivity.Purpose>(ModifyPostActivity.PURPOSE)!!
    private var pictureChanged = false

    init {
        viewModelScope.registerExceptionHandler { e ->
            mTaskFlow += ModifyPostTask.ShowError(e.uiText)
        }

        if (purpose is ModifyPostActivity.Purpose.EditPost) {
            val post = purpose.post

            val mutable = post.mutable
            val data = post.data

            pictureUri = data.pictureUri
            name = mutable.name.asUiText()
            surname = mutable.surname.asUiText()
            parentName = mutable.parentName.asUiText()
            location = mutable.location.asUiText()
            donorsRequired = mutable.donorsRequired.toString().asUiText()
            description = mutable.description.asUiText()
        }
    }

    override fun onEvent(event: ModifyPostEvent) {
        when (event) {
            is ModifyPostEvent.PictureChange -> {
                pictureUri = event.uri
                pictureChanged = true
            }

            is ModifyPostEvent.NameChange -> name = event.name.asUiText()
            is ModifyPostEvent.SurnameChange -> surname = event.surname.asUiText()
            is ModifyPostEvent.ParentNameChange -> parentName = event.parentName.asUiText()

            is ModifyPostEvent.LocationChange -> location = event.location.asUiText()
            is ModifyPostEvent.DonorsRequiredChange -> {
                if (event.donorsRequired == "0") return
                if (!event.donorsRequired.isDigitsOnly()) return
                if (event.donorsRequired.isNotEmpty() && event.donorsRequired.toInt() > 20) return

                donorsRequired = event.donorsRequired.asUiText()
            }

            is ModifyPostEvent.BloodClick -> setExpanded(blood = !bloodExpanded)
            is ModifyPostEvent.BloodChange -> {
                blood = event.blood
                setExpanded(blood = false)
            }

            is ModifyPostEvent.DescriptionChange -> description = event.description.asUiText()

            is ModifyPostEvent.Agreement1Click -> agreement1Checked = !agreement1Checked
            is ModifyPostEvent.Agreement2Click -> agreement2Checked = !agreement2Checked

            is ModifyPostEvent.Dismiss -> setExpanded()

            is ModifyPostEvent.Finish -> viewModelScope.loadSafely {
                val donors = donorsRequired.toString()

                val post = Post.Mutable(
                    name = name.toString(),
                    surname = surname.toString(),
                    parentName = parentName.toString(),
                    location = location.toString(),
                    blood = blood,
                    donorsRequired = if (donors == "") 0 else donors.toInt(),
                    description = description.toString()
                )

                when (purpose) {
                    is ModifyPostActivity.Purpose.CreatePost -> {
                        val id = createPost(post)

                        if (pictureUri != null) {
                            updatePostPicture(id, pictureUri!!)
                        }
                    }

                    is ModifyPostActivity.Purpose.EditPost -> {
                        val id = purpose.post.data.id

                        val updatePostJob = launch { updatePost(id, post) }
                        val updatePictureJob = if (pictureChanged) {
                            launch { updatePostPicture(id, pictureUri!!) }
                        } else {
                            null
                        }

                        updatePostJob.join()
                        updatePictureJob?.join()
                    }
                }

                mTaskFlow += ModifyPostTask.Close
            }
        }
    }

    private fun setExpanded(
        blood: Boolean = false
    ) {
        bloodExpanded = blood
    }
}