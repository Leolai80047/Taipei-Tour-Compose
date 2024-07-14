package com.leodemo.taipei_tour_compose.ui.components.attraction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import com.leodemo.taipei_tour_compose.ui.theme.color_top_app_bar_container

@Composable
fun MoveTopButton(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onClick: () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInHorizontally { fullWidth ->
            fullWidth * 2
        },
        exit = slideOutHorizontally { fullWidth ->
            fullWidth * 2
        }
    ) {
        Image(
            modifier = Modifier
                .size(60.dp)
                .drawBehind {
                    drawCircle(
                        color = color_top_app_bar_container,
                        radius = 30.dp.toPx()
                    )
                }
                .clickable(indication = null, interactionSource = null) {
                    onClick()
                },
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = null
        )
    }
}