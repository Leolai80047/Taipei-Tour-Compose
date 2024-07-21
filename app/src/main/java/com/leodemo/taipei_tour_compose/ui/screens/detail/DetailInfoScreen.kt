package com.leodemo.taipei_tour_compose.ui.screens.detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.leodemo.taipei_tour_compose.R
import com.leodemo.taipei_tour_compose.ui.components.detail.HyperLinkText
import com.leodemo.taipei_tour_compose.ui.theme.color_hyper_link_text
import com.leodemo.taipei_tour_compose.ui.utils.LocalizeContext
import com.leodemo.taipei_tour_compose.ui.utils.dpToSp

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun SharedTransitionScope.DetailInfoScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigateWebScreen: (String) -> Unit,
    onBack: () -> Unit
) {
    val data by viewModel.item.observeAsState()
    val scrollState = rememberScrollState()
    Column {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Text(
                    text = LocalizeContext.current.getString(R.string.app_name),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.dp.dpToSp()
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    onBack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .testTag("DetailScrollView")
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.secondary)
                .padding(horizontal = 5.dp)
        ) {
            data?.let { item ->
                GlideImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .sharedElement(
                            state = rememberSharedContentState(key = "image-${item.id}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                        ),
                    model = item.imageUrl,
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                    loading = placeholder(R.drawable.img_placeholder),
                    failure = placeholder(R.drawable.img_not_found),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.testTag("Title"),
                    text = item.name,
                    fontSize = 20.dp.dpToSp(),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = item.introduction
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "${LocalizeContext.current.getString(R.string.address)}\n${item.address}"
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "${LocalizeContext.current.getString(R.string.last_update_time)}\n${item.modified}"
                )
                Spacer(modifier = Modifier.height(30.dp))
                HyperLinkText(
                    modifier = Modifier
                        .testTag("UrlText")
                        .clickable {
                            onNavigateWebScreen(item.url)
                        },
                    text = item.url,
                    linkColor = color_hyper_link_text
                )
            }

        }
    }

}