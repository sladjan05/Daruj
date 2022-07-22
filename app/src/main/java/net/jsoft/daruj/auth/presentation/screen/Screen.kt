package net.jsoft.daruj.auth.presentation.screen

sealed class Screen(
    val route: String
) {
    object Phone : Screen("phone")
    object Verification : Screen("verification")
}