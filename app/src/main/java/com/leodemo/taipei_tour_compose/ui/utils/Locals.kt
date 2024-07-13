package com.leodemo.taipei_tour_compose.ui.utils

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf

val LocalizeContext = staticCompositionLocalOf<Context> {
    throw Exception("CompositionLocal LocalizeContext not present")
}