package com.leodemo.taipei_tour_compose.ui.components.attraction

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.leodemo.taipei_tour_compose.R
import com.leodemo.taipei_tour_compose.domain.model.AttractionInfo
import com.leodemo.taipei_tour_compose.ui.theme.color_item_attraction_background
import com.leodemo.taipei_tour_compose.ui.theme.color_shadow_tint
import com.leodemo.taipei_tour_compose.ui.utils.dpToSp
import com.leodemo.taipei_tour_compose.ui.utils.modifier.shadow

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AttractionItem(
    modifier: Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    item: AttractionInfo
) {
    Row(
        modifier = modifier
            .shadow(
                color = color_shadow_tint,
                offsetX = (3).dp,
                offsetY = (3).dp,
                radius = 10.dp,
                blurRadius = 0.dp
            )
            .clip(RoundedCornerShape(10.dp))
            .background(color_item_attraction_background)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .size(120.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = "image-${item.id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                )
                .skipToLookaheadSize(),
            model = item.imageUrl,
            loading = placeholder(R.drawable.img_placeholder),
            failure = placeholder(R.drawable.img_not_found),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(5.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(
                text = item.name,
                fontSize = 20.dp.dpToSp(),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = item.introduction,
                fontSize = 16.dp.dpToSp(),
                maxLines = 4,
                lineHeight = 20.dp.dpToSp(),
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(5.dp))
    }
}