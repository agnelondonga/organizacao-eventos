package com.example.eventosapp.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventosapp.R
import com.example.eventosapp.ui.theme.CreateAccountScreen
import com.example.eventosapp.ui.theme.LoginScreen
import com.example.eventosapp.ui.theme.*

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()

    NavHost(navController = navController, startDestination = "welcome_back") {

        composable("welcome_back") {
            WelcomeScreen(navController = navController)
        }

        composable("login") {
            LoginScreen(navController = navController)
        }

        composable("create_account") {
            CreateAccountScreen(navController = navController)
        }

        composable("event_selection") {
            EventSelectionScreen(navController = navController)
        }

        composable(
            route = "event_details/{eventName}",
            arguments = listOf(navArgument("eventName") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventName = backStackEntry.arguments?.getString("eventName") ?: ""
            EventDetailsScreen(eventName = eventName, navController = navController)
        }

        composable("event_creation") {
            EventCreationScreen(navController = navController)
        }

        composable(
            route = "event_creation/{eventName}",
            arguments = listOf(navArgument("eventName") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventName = backStackEntry.arguments?.getString("eventName")
            EventCreationScreen(navController = navController, eventName = eventName)
        }

        composable(
            route = "custom_event_creation/{eventName}",
            arguments = listOf(navArgument("eventName") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventName = backStackEntry.arguments?.getString("eventName") ?: ""
            CustomEventCreationScreen(eventName = eventName, navController = navController)
        }

        composable("event_location") {
            EventLocationScreen(navController = navController)
        }

        composable(
            route = "location_details/{locationName}",
            arguments = listOf(navArgument("locationName") { type = NavType.StringType })
        ) { backStackEntry ->
            val locationName = backStackEntry.arguments?.getString("locationName") ?: ""
            LocationDetailsScreen(locationName = locationName, navController = navController)
        }

        composable(
            route = "schedule_day/{locationName}",
            arguments = listOf(navArgument("locationName") { type = NavType.StringType })
        ) { backStackEntry ->
            val locationName = backStackEntry.arguments?.getString("locationName") ?: ""
            ScheduleDayScreen(locationName = locationName, navController = navController)
        }

        composable("task_list") {
            TaskListScreen(navController = navController)
        }

        composable(
            route = "task_details/{taskName}/{taskStatus}/{taskIcon}",
            arguments = listOf(
                navArgument("taskName") { type = NavType.StringType },
                navArgument("taskStatus") { type = NavType.StringType },
                navArgument("taskIcon") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val taskName = backStackEntry.arguments?.getString("taskName") ?: ""
            val taskStatus = backStackEntry.arguments?.getString("taskStatus") ?: ""
            val taskIcon = backStackEntry.arguments?.getString("taskIcon") ?: ""
            TaskDetailsScreen(
                taskName = taskName,
                taskStatus = taskStatus,
                taskIcon = taskIcon,
                navController = navController
            )
        }

        composable("task_edit") {
            TaskEditScreen(navController = navController)
        }

        composable(
            route = "task_edit/{taskName}/{taskStatus}/{taskIcon}",
            arguments = listOf(
                navArgument("taskName") { type = NavType.StringType },
                navArgument("taskStatus") { type = NavType.StringType },
                navArgument("taskIcon") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val taskName = backStackEntry.arguments?.getString("taskName") ?: ""
            val taskStatus = backStackEntry.arguments?.getString("taskStatus") ?: ""
            val taskIcon = backStackEntry.arguments?.getString("taskIcon") ?: ""
            TaskEditScreen(
                taskName = taskName,
                taskStatus = taskStatus,
                taskIcon = taskIcon,
                navController = navController
            )
        }

        composable("employee_management") {
            EmployeeManagementScreen(navController = navController)
        }

        composable(
            route = "menu_configuration/{eventName}",
            arguments = listOf(navArgument("eventName") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventName = backStackEntry.arguments?.getString("eventName") ?: ""
            MenuConfigurationScreen(eventName = eventName, navController = navController)
        }

        composable(
            route = "employee_details/{employeeName}",
            arguments = listOf(navArgument("employeeName") { type = NavType.StringType })
        ) { backStackEntry ->
            val employeeName = backStackEntry.arguments?.getString("employeeName") ?: ""
            val employee = viewModel.employees.value.find { it.name == employeeName }
                ?: Employee("", "", false, R.drawable.ic_other)
            EmployeeDetailsScreen(navController = navController, employee = employee)
        }

        composable(
            route = "employee_edit/{employeeName}",
            arguments = listOf(navArgument("employeeName") { type = NavType.StringType })
        ) { backStackEntry ->
            val employeeName = backStackEntry.arguments?.getString("employeeName") ?: ""
            val employee = viewModel.employees.value.find { it.name == employeeName }
            EmployeeEditScreen(navController = navController, employee = employee, viewModel = viewModel)
        }

        composable("employee_edit") {
            EmployeeEditScreen(navController = navController, employee = null, viewModel = viewModel)
        }
    }
}
