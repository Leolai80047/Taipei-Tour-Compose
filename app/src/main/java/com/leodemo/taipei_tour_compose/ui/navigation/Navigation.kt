package com.leodemo.taipei_tour_compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leodemo.taipei_tour_compose.ui.main.MainViewModel
import com.leodemo.taipei_tour_compose.ui.screens.DetailInfoScreen
import com.leodemo.taipei_tour_compose.ui.screens.MainScreen
import com.leodemo.taipei_tour_compose.ui.screens.Screen
import com.leodemo.taipei_tour_compose.ui.screens.WebScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Screen.MainScreen) {
        composable<Screen.MainScreen> {
            MainScreen(
                viewModel = viewModel,
                onItemClick = {
                    viewModel.currentItem.value = it
                    navController.navigate(Screen.DetailInfoScreen)
                }
            )
        }
        composable<Screen.DetailInfoScreen> {
            DetailInfoScreen(
                viewModel = viewModel,
                onNavigateWebScreen = {
                    navController.navigate(Screen.WebScreen)
                },
                onBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<Screen.WebScreen> {
            WebScreen(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}