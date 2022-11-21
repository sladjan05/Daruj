package net.jsoft.daruj.create_account.presentation

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import net.jsoft.daruj.common.util.setScreenContent
import net.jsoft.daruj.create_account.presentation.screen.ModifyAccountScreen
import net.jsoft.daruj.create_account.presentation.viewmodel.ModifyAccountViewModel
import net.jsoft.daruj.main.presentation.viewmodel.profile.ProfileEvent

@AndroidEntryPoint
class ModifyAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras!!
        val intention = extras.get(Intention) as Intention

        setScreenContent { ModifyAccountScreen(intention) }
    }

    @Parcelize
    enum class Intention : Parcelable {
        CreateAccount,
        EditAccount
    }

    companion object {
        const val Intention = "intention"
    }
}