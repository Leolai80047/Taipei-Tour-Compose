package com.leodemo.taipei_tour_compose.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun Dp.dpToSp(): TextUnit {
    return LocalDensity.current.run {
        this@dpToSp.toSp()
    }
}