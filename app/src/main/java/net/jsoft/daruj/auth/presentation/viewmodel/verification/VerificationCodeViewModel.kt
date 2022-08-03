package net.jsoft.daruj.auth.presentation.viewmodel.verification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import net.jsoft.daruj.common.presentation.viewmodel.BaseViewModel
import net.jsoft.daruj.common.util.asUiText

class VerificationCodeViewModel : BaseViewModel<VerificationCodeEvent, Nothing>() {

    var code by mutableStateOf("".asUiText())
        private set

    override fun onEvent(event: VerificationCodeEvent) {
        when (event) {
            is VerificationCodeEvent.NumberClick -> {
                val numberString = event.number.toString()
                val codeString = code.toString()

                if (codeString.length == 6) return

                code = (codeString + numberString).asUiText()
            }

            is VerificationCodeEvent.DeleteClick -> {
                val codeString = code.toString()

                if (codeString.isEmpty()) return

                code = codeString.dropLast(1).asUiText()
            }
        }
    }
}