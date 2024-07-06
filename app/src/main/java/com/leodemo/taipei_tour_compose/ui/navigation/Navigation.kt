package com.leodemo.taipei_tour_compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leodemo.taipei_tour_compose.presentation.utils.LocaleUtils
import com.leodemo.taipei_tour_compose.ui.main.MainViewModel
import com.leodemo.taipei_tour_compose.ui.screens.DetailInfoScreen
import com.leodemo.taipei_tour_compose.ui.screens.MainScreen
import com.leodemo.taipei_tour_compose.ui.screens.Screen
import com.leodemo.taipei_tour_compose.ui.screens.WebScreen

@Composable
fun Navigation(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val context = LocalContext.current
    var localizeContext by remember {
        mutableStateOf(
            LocaleUtils.getLocalizeContext(
                context,
                LocaleUtils.getLocale(context).get(0) ?: throw Exception("App locale is Empty!")
            )
        )
    }
    NavHost(navController = navController, startDestination = Screen.MainScreen) {
        composable<Screen.MainScreen> {
            MainScreen(
                viewModel = viewModel,
                localizeContext = localizeContext,
                onLocaleChange = { locale ->
                    localizeContext = LocaleUtils.getLocalizeContext(context, locale)
                    LocaleUtils.setLocale(context, locale)
                },
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