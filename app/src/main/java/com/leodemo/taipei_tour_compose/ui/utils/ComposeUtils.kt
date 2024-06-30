package com.leodemo.taipei_tour_compose.ui.utils

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.leodemo.taipei_tour_compose.ui.theme.color_shimmer_attraction_item_gradient_end
import com.leodemo.taipei_tour_compose.ui.theme.color_shimmer_attraction_item_gradient_start

@Composable
fun Dp.dpToSp(): TextUnit {
    return LocalDensity.current.run {
        this@dpToSp.toSp()
    }
}

fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    radius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }
            frameworkPaint.color = color.toArgb()

            val leftPixel = offsetX.toPx()
            val topPixel = offsetY.toPx()
            val rightPixel = size.width + topPixel
            val bottomPixel = size.height + leftPixel

            canvas.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = radius.toPx(),
                radiusY = radius.toPx(),
                paint = paint,
            )
        }
    }
)

@Composable
fun HyperLinkText(
    modifier: Modifier,
    text: String,
    linkColor: Color
) {
    val annotatedText = buildAnnotatedString {
        withStyle(SpanStyle(
            color = linkColor,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )) {
            append(text)
        }
    }
    Text(
        modifier = modifier,
        text = annotatedText
    )
}

@Composable
fun ShimmerAttractionItem(
    modifier: Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Box(modifier = Modifier
            .size(100.dp)
            .shimmerEffect())
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