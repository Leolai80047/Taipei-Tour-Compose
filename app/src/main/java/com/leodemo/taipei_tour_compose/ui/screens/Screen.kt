package com.leodemo.taipei_tour_compose.ui.screens

import com.leodemo.taipei_tour_compose.domain.model.AttractionInfo
import com.leodemo.taipei_tour_compose.ui.navigation.AttractionInfoType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

sealed interface Screen {
    @Serializable
    data object AttractionScreen : Screen

    @Serializable
    data class DetailInfoScreen(val attractionInfo: AttractionInfo) : Screen {
        companion object {
            val typeMap = mapOf(typeOf<AttractionInfo>() to AttractionInfoType)
        }
    }

    @Serializable
    data class WebScreen(val url: String) : Screen
}