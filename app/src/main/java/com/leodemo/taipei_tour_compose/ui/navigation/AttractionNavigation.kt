package com.leodemo.taipei_tour_compose.ui.navigation

import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leodemo.taipei_tour_compose.ui.utils.language.LocaleUtils
import com.leodemo.taipei_tour_compose.ui.main.MainViewModel
import com.leodemo.taipei_tour_compose.ui.screens.DetailInfoScreen
import com.leodemo.taipei_tour_compose.ui.screens.MainScreen
import com.leodemo.taipei_tour_compose.ui.screens.Screen
import com.leodemo.taipei_tour_compose.ui.screens.WebScreen
import com.leodemo.taipei_tour_compose.ui.theme.color_top_app_bar_container
import com.leodemo.taipei_tour_compose.ui.utils.LocalizeContext

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AttractionNavigation(viewModel: MainViewModel = hiltViewModel()) {
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
    CompositionLocalProvider(LocalizeContext provides localizeContext) {
        SharedTransitionLayout {
            NavHost(navController = navController, startDestination = Screen.MainScreen) {
                composable<Screen.MainScreen> {
                    MainScreen(
                        viewModel = viewModel,
                        animatedVisibilityScope = this,
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
                        animatedVisibilityScope = this,
                        onNavigateWebScreen = {
//                        navController.navigate(Screen.WebScreen)
                            val params = CustomTabColorSchemeParams.Builder()
                                .setToolbarColor(color_top_app_bar_container.toArgb())
                                .build()
                            CustomTabsIntent.Builder()
                                .setDefaultColorSchemeParams(params)
                                .setShowTitle(true)
                                .build()
                                .launchUrl(
                                    context,
                                    Uri.parse(viewModel.currentItem.value?.url ?: "about:blank")
                                )
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
    }
}