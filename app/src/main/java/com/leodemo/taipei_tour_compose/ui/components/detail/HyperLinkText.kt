package com.leodemo.taipei_tour_compose.ui.components.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

@Composable
fun HyperLinkText(
    modifier: Modifier,
    text: String,
    linkColor: Color
) {
    val annotatedText = buildAnnotatedString {
        withStyle(
            SpanStyle(
            color = linkColor,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
        ) {
            append(text)
        }
    }
    Text(
        modifier = modifier,
        text = annotatedText
    )
}