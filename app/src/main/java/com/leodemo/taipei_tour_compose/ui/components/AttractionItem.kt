package com.leodemo.taipei_tour_compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.leodemo.taipei_tour.data.api.AttractionResponse
import com.leodemo.taipei_tour_compose.R
import com.leodemo.taipei_tour_compose.ui.theme.color_item_attraction_background
import com.leodemo.taipei_tour_compose.ui.theme.color_shadow_tint
import com.leodemo.taipei_tour_compose.ui.utils.dpToSp
import com.leodemo.taipei_tour_compose.ui.utils.shadow

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AttractionItem(
    modifier: Modifier,
    item: AttractionResponse.Data
) {
    ConstraintLayout(
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
            .padding(10.dp)

    ) {
        val (thumbnail, title, overview, iconNext) = createRefs()
        GlideImage(
            modifier = Modifier
                .constrainAs(thumbnail) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .size(120.dp),
            model = item.getImage(),
            loading = placeholder(R.drawable.img_placeholder),
            failure = placeholder(R.drawable.img_not_found),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Text(
            modifier = Modifier.constrainAs(title) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                top.linkTo(thumbnail.top)
                start.linkTo(thumbnail.end, margin = 5.dp)
                end.linkTo(iconNext.start)
            },
            text = item.name,
            fontSize = 20.dp.dpToSp(),
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            modifier = Modifier
                .constrainAs(iconNext) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 5.dp)
                }
                .size(20.dp),
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null
        )
        Text(
            modifier = Modifier.constrainAs(overview) {
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
                top.linkTo(title.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(title.start)
                end.linkTo(iconNext.start)
            },
            text = item.introduction,
            fontSize = 16.dp.dpToSp(),
            maxLines = 4,
            lineHeight = 20.dp.dpToSp(),
            overflow = TextOverflow.Ellipsis
        )
    }
}