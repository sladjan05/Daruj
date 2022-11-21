package net.jsoft.daruj.main.presentation.screen

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.jsoft.daruj.common.util.showShortToast
import net.jsoft.daruj.main.presentation.component.BottomNavigationBar
import net.jsoft.daruj.main.presentation.viewmodel.MainEvent
import net.jsoft.daruj.main.presentation.viewmodel.MainTask
import net.jsoft.daruj.main.presentation.viewmodel.MainViewModel

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context as Activity

    val scope = rememberCoroutineScope()

    val navController = rememberAnimatedNavController()
    val homeController = remember { HomeScreenController() }

    fun getSign(
        initialRoute: String,
        targetRoute: String
    ): Int {
        val initialScreen = Screen.fromRoute(initialRoute)
        val targetScreen = Screen.fromRoute(targetRoute)

        val initialIndex = Screen.screens().indexOf(initialScreen)
        val targetIndex = Screen.screens().indexOf(targetScreen)

        return if (initialIndex < targetIndex) 1 else -1
    }

    LaunchedEffect(Unit) {
        viewModel.taskFlow.collectLatest { task ->
            when (task) {
                is MainTask.ShowError -> context.showShortToast(task.message.getValue(context))
            }
        }
    }

    val floatingRoutes = Screen.floatingScreens().map(Screen::route)

    LaunchedEffect(viewModel.page) {
        if (navController.currentDestination?.route in floatingRoutes) navController.popBackStack()

        navController.popBackStack()
        navController.navigate(Screen.screens()[viewModel.page].route)
    }

    val enterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
        val initialRoute = initialState.destination.route!!
        val targetRoute = targetState.destination.route!!

        when {
            targetRoute in floatingRoutes -> slideInVertically { it }
            initialRoute in floatingRoutes -> fadeIn()

            else -> {
                val sign = getSign(
                    initialRoute = initialRoute,
                    targetRoute = targetRoute
                )

                slideInHorizontally { sign * it }
            }
        }
    }

    val exitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
        val initialRoute = initialState.destination.route!!
        val targetRoute = targetState.destination.route!!

        when {
            initialRoute in floatingRoutes -> slideOutVertically { it } + fadeOut()
            targetRoute in floatingRoutes -> fadeOut()

            else -> {
                val sign = getSign(
                    initialRoute = initialRoute,
                    targetRoute = targetRoute
                )

                slideOutHorizontally { -sign * it }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.fillMaxSize(),
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = enterTransition,
            popExitTransition = exitTransition
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                    controller = homeController
                )
            }

            composable(Screen.Search.route) { SearchScreen(navController) }

            composable(Screen.Saved.route) { SavedScreen(navController) }

            composable(Screen.Profile.route) { ProfileScreen(navController) }

            composable(
                route = Screen.DetailedPost.route,
                arguments = listOf(
                    navArgument(Screen.DetailedPost.POST_ID) { type = NavType.StringType }
                )
            ) { DetailedPostScreen(navController) }

            composable(
                route = Screen.Receipts.route,
                arguments = listOf(
                    navArgument(Screen.Receipts.POST_ID) { type = NavType.StringType }
                )
            ) { ReceiptsScreen() }
        }

        BottomNavigationBar(
            currentIndex = viewModel.page,
            onChange = { index ->
                if (index == Screen.screens().indexOf(Screen.Home)) scope.launch { homeController.scrollToTop() }
                viewModel.onEvent(MainEvent.PageChange(index))
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}