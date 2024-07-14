package com.leodemo.taipei_tour_compose.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColors = lightColorScheme(
    primary = theme_light_primary,
    onPrimary = theme_light_onPrimary,
    secondary = theme_light_secondary,
    secondaryContainer = theme_light_secondaryContainer
)


private val DarkColors = darkColorScheme(
    primary = theme_dark_primary,
    onPrimary = theme_dark_onPrimary,
    secondary = theme_dark_secondary,
    secondaryContainer = theme_dark_secondaryContainer
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insets = WindowCompat.getInsetsController(window, view)
            window.statusBarColor = colors.primary.toArgb() // choose a status bar color
//            window.navigationBarColor = colorScheme.whatever.toArgb() // choose a navigation bar color
            insets.isAppearanceLightStatusBars = !useDarkTheme
//            insets.isAppearanceLightNavigationBars = !useDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}