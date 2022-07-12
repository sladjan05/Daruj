package net.jsoft.daruj.auth.presentation.screen

import net.jsoft.daruj.common.presentation.screen.BasicScreen

sealed class Screen(
    route: String
) : BasicScreen(route) {
    object Phone : Screen("phone")
    object Verification : Screen("verification")
}