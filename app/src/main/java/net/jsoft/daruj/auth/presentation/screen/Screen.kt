package net.jsoft.daruj.auth.presentation.screen

import net.jsoft.daruj.common.presentation.screen.BaseScreen

sealed class Screen(
    route: String
) : BaseScreen("auth/$route") {
    object Phone : Screen("phone")
    object Verification : Screen("verification/{fullPhoneNumber}") {
        const val FULL_PHONE_NUMBER = "fullPhoneNumber"
    }
}