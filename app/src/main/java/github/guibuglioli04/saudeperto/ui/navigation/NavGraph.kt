package github.guibuglioli04.saudeperto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import github.guibuglioli04.saudeperto.ui.screens.FormScreen
import github.guibuglioli04.saudeperto.ui.screens.HomeScreen
import github.guibuglioli04.saudeperto.ui.screens.ListScreen
import github.guibuglioli04.saudeperto.ui.viewmodel.UnidadeSaudeViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object List : Screen("list")
    object Form : Screen("form/{unidadeId}") {
        fun createRoute(unidadeId: Int) = "form/$unidadeId"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: UnidadeSaudeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToList = { navController.navigate(Screen.List.route) },
                onNavigateToForm = { navController.navigate(Screen.Form.createRoute(0)) }
            )
        }

        composable(Screen.List.route) {
            ListScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToForm = { id -> navController.navigate(Screen.Form.createRoute(id)) }
            )
        }

        composable(
            route = Screen.Form.route,
            arguments = listOf(
                navArgument("unidadeId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val unidadeId = backStackEntry.arguments?.getInt("unidadeId") ?: 0
            FormScreen(
                viewModel = viewModel,
                unidadeId = unidadeId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
