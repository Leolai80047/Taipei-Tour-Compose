package com.leodemo.taipei_tour_compose.ui.utils.modifier

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.leodemo.taipei_tour_compose.ui.theme.color_shimmer_attraction_item_gradient_end
import com.leodemo.taipei_tour_compose.ui.theme.color_shimmer_attraction_item_gradient_start

fun Modifier.shimmerEffect() = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2F * size.width,
        targetValue = 2F * size.width,
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = ""
    )

    background(
        Brush.linearGradient(
            colors = listOf(
                color_shimmer_attraction_item_gradient_start,
                color_shimmer_attraction_item_gradient_end,
                color_shimmer_attraction_item_gradient_start
            ),
            start = Offset(startOffsetX, (size.height / 2).toFloat()),
            end = Offset((startOffsetX + size.width), (size.height / 2).toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}