package net.jsoft.daruj.common.presentation.screen

abstract class BaseScreen(val route: String) {
    fun route(vararg args: Pair<String, String>): String {
        var route = this.route

        for (arg in args) {
            route = route.replace(
                oldValue = "{${arg.first}}",
                newValue = arg.second
            )
        }

        return route
    }
}