package net.jsoft.daruj.comment.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import net.jsoft.daruj.comment.presentation.screen.CommentsScreen
import net.jsoft.daruj.common.util.setScreenContent

@AndroidEntryPoint
class CommentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScreenContent { CommentsScreen() }
    }
}