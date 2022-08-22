package net.jsoft.daruj.modify_post.presentation

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import net.jsoft.daruj.R
import net.jsoft.daruj.common.utils.setScreenContent
import net.jsoft.daruj.common.utils.value
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.modify_post.presentation.screen.ModifyPostScreen

@AndroidEntryPoint
class ModifyPostActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras!!
        val purpose = extras.get(PURPOSE) as Purpose

        setScreenContent {
            ModifyPostScreen(
                title = when (purpose) {
                    is Purpose.CreatePost -> R.string.tx_create_new_post.value
                    is Purpose.EditPost -> R.string.tx_edit_post.value
                },
                buttonText = when (purpose) {
                    is Purpose.CreatePost -> R.string.tx_create_post.value
                    is Purpose.EditPost -> R.string.tx_save.value
                }
            )
        }
    }

    @Parcelize
    sealed class Purpose : Parcelable {
        @Parcelize
        object CreatePost : Purpose(), Parcelable

        @Parcelize
        class EditPost(val post: Post) : Purpose(), Parcelable
    }

    companion object {
        const val PURPOSE = "PURPOSE"
    }
}