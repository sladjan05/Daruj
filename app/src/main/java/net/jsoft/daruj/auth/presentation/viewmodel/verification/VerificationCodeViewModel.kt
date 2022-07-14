package net.jsoft.daruj.auth.presentation.viewmodel.verification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import net.jsoft.daruj.common.presentation.viewmodel.BasicViewModel
import net.jsoft.daruj.common.util.UiText.Companion.asUiText

class VerificationCodeViewModel : BasicViewModel<VerificationCodeEvent, Nothing>() {

    var code by mutableStateOf("".asUiText())
        private set

    override fun onEvent(event: VerificationCodeEvent) {
        when (event) {
            is VerificationCodeEvent.CodeChange -> {
                code = event.code.asUiText()
            }
        }
    }
}