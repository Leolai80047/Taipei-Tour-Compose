package com.leodemo.taipei_tour_compose.ui.screens

import com.leodemo.taipei_tour.data.api.AttractionResponse
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object MainScreen : Screen
    @Serializable
    data object DetailInfoScreen : Screen
    @Serializable
    data object WebScreen : Screen
}