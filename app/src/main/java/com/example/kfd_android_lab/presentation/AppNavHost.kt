package com.example.kfd_android_lab.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kfd_android_lab.data.FakeTasksRepository

object Routes {
    const val TASKS = "tasks"
    const val TASK_DETAILED = "task"
    const val SETTINGS = "settings"
}

@Composable
fun AppNavHost(
    repository: FakeTasksRepository,
    darkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val viewModel = remember(repository) { TasksViewModel(repository) }

    NavHost(
        navController = navController,
        startDestination = Routes.TASKS,
        modifier = modifier
    ) {
        composable(Routes.TASKS) {
            TasksRoute(
                viewModel,
                onTaskClick = { id -> navController.navigate("${Routes.TASK_DETAILED}/$id") },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) }
            )
        }

        composable(
            "${Routes.TASK_DETAILED}/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            }
            )) { entry ->
            val taskId = entry.arguments?.getString("id").orEmpty()
            TaskDetailRoute(taskId, viewModel, onBack = navController::popBackStack)
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                darkTheme = darkTheme,
                onThemeChanged = onThemeChanged,
                onBack = { navController.popBackStack() }
            )
        }
    }
}