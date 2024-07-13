package com.leodemo.taipei_tour_compose.ui.components.attraction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leodemo.taipei_tour_compose.ui.utils.modifier.shimmerEffect

@Composable
fun ShimmerAttractionItem(
    modifier: Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7F)
                    .height(20.dp)
                    .shimmerEffect()
            )
        }
    }
}