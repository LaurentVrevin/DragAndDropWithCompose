package com.laurentvrevin.draganddropwithcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.laurentvrevin.draganddropwithcompose.presentation.composable.screens.AddTaskScreen
import com.laurentvrevin.draganddropwithcompose.presentation.composable.screens.TaskListScreen
import com.laurentvrevin.draganddropwithcompose.presentation.viewmodel.TaskViewModel

@Composable
fun NavGraph(navController: NavHostController, taskViewModel: TaskViewModel) {
    NavHost(navController = navController, startDestination = "taskList") {
        composable("taskList") {
            TaskListScreen(navController, taskViewModel)
        }
        composable("addTask") {
            AddTaskScreen(navController, taskViewModel)
        }
    }
}
