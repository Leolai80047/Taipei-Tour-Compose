package com.leodemo.taipei_tour_compose.ui.navigation

import android.os.Bundle
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.leodemo.taipei_tour_compose.ui.screens.Screen
import com.leodemo.taipei_tour_compose.ui.screens.attraction.AttractionScreen
import com.leodemo.taipei_tour_compose.ui.screens.detail.DetailInfoScreen
import com.leodemo.taipei_tour_compose.ui.screens.web.WebScreen
import com.leodemo.taipei_tour_compose.ui.utils.LocalizeContext
import com.leodemo.taipei_tour_compose.ui.utils.language.LocaleUtils

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AttractionNavigation() {
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
            NavHost(
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                },
                navController = navController,
                startDestination = Screen.AttractionScreen
            ) {
                composable<Screen.AttractionScreen> {
                    AttractionScreen(
                        animatedVisibilityScope = this,
                        onLocaleChange = { locale ->
                            localizeContext = LocaleUtils.getLocalizeContext(context, locale)
                            LocaleUtils.setLocale(context, locale)
                        },
                        onItemClick = { info ->
                            navController.navigate(Screen.DetailInfoScreen(info))
                            FirebaseAnalytics.getInstance(context).logEvent("attraction_info") {
                                param("id", info.id)
                                param("name", info.name)
                            }
                        }
                    )
                }
                composable<Screen.DetailInfoScreen>(
                    typeMap = Screen.DetailInfoScreen.typeMap
                ) {
                    DetailInfoScreen(
                        animatedVisibilityScope = this,
                        onNavigateWebScreen = { url ->
                            navController.navigate(Screen.WebScreen(url))
//                            val params = CustomTabColorSchemeParams.Builder()
//                                .setToolbarColor(color_top_app_bar_container.toArgb())
//                                .build()
//                            CustomTabsIntent.Builder()
//                                .setDefaultColorSchemeParams(params)
//                                .setShowTitle(true)
//                                .build()
//                                .launchUrl(
//                                    context,
//                                    Uri.parse(url)
//                                )
                        },
                        onBack = {
                            navController.navigateUp()
                        }
                    )
                }
                composable<Screen.WebScreen> {
                    WebScreen(
                        onBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}