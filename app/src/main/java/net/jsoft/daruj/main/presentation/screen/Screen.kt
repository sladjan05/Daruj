package net.jsoft.daruj.main.presentation.screen

import net.jsoft.daruj.common.presentation.screen.BaseScreen

sealed class Screen(route: String) : BaseScreen("main/$route") {
    object Home : Screen("home")
    object Search : Screen("search")
    object Saved : Screen("saved")
    object Profile : Screen("profile")

    object DetailedPost : Screen("detailedPost/{postId}") {
        const val POST_ID = "postId"
    }

    object Receipts : Screen("receipts/{postId}") {
        const val POST_ID = "postId"
    }

    companion object {
        fun screens() = listOf(
            Home,
            Search,
            Saved,
            Profile,
            DetailedPost,
            Receipts
        )

        fun floatingScreens() = listOf(
            DetailedPost,
            Receipts
        )

        fun fromRoute(route: String): Screen = screens().first { screen -> screen.route == route }
    }
}