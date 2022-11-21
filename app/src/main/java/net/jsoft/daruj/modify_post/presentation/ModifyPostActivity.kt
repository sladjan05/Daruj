package net.jsoft.daruj.modify_post.presentation

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import net.jsoft.daruj.R
import net.jsoft.daruj.common.util.setScreenContent
import net.jsoft.daruj.common.util.value
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.modify_post.presentation.screen.ModifyPostScreen

@AndroidEntryPoint
class ModifyPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras!!
        val intention = extras.get(Intention) as Intention

        setScreenContent { ModifyPostScreen(intention) }
    }

    @Parcelize
    sealed interface Intention : Parcelable {

        @Parcelize
        object CreatePost : Intention, Parcelable

        @Parcelize
        class EditPost(val post: Post) : Intention, Parcelable
    }

    companion object {
        const val Intention = "intention"
    }
}