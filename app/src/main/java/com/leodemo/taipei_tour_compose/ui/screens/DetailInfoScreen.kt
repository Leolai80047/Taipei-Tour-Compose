package com.leodemo.taipei_tour_compose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.leodemo.taipei_tour_compose.R
import com.leodemo.taipei_tour_compose.ui.main.MainViewModel
import com.leodemo.taipei_tour_compose.ui.theme.color_attraction_main_background
import com.leodemo.taipei_tour_compose.ui.theme.color_hyper_link_text
import com.leodemo.taipei_tour_compose.ui.theme.color_top_app_bar_container
import com.leodemo.taipei_tour_compose.ui.utils.HyperLinkText
import com.leodemo.taipei_tour_compose.ui.utils.dpToSp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun DetailInfoScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onNavigateWebScreen: () -> Unit,
    onBack: () -> Unit
) {
    val data by viewModel.currentItem.observeAsState()
    val scrollState = rememberScrollState()
    Column {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = color_top_app_bar_container
            ),
            title = {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 20.dp.dpToSp()
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    onBack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .background(color_attraction_main_background)
        ) {
            data?.let { item ->
                ConstraintLayout {
                    val (cover, title, description, address, lastUpdate, url) = createRefs()

                    GlideImage(
                        modifier = Modifier
                            .constrainAs(cover) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .fillMaxWidth()
                            .height(200.dp),
                        model = item.getImage(),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center,
                        loading = placeholder(R.drawable.img_placeholder),
                        failure = placeholder(R.drawable.img_not_found),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.constrainAs(title) {
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
                            top.linkTo(cover.bottom, 20.dp)
                            start.linkTo(parent.start, 5.dp)
                            end.linkTo(parent.end, 5.dp)
                        },
                        text = item.name,
                        fontSize = 20.dp.dpToSp(),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.constrainAs(description) {
                            width = Dimension.fillToConstraints
                            top.linkTo(title.bottom, 20.dp)
                            start.linkTo(title.start)
                            end.linkTo(title.end)
                        },
                        text = item.introduction
                    )
                    Text(
                        modifier = Modifier.constrainAs(address) {
                            width = Dimension.fillToConstraints
                            top.linkTo(description.bottom, 30.dp)
                            start.linkTo(description.start)
                            end.linkTo(description.end)
                        },
                        text = item.address
                    )
                    Text(
                        modifier = Modifier.constrainAs(lastUpdate) {
                            width = Dimension.fillToConstraints
                            top.linkTo(address.bottom, 30.dp)
                            start.linkTo(address.start)
                            end.linkTo(address.end)
                        },
                        text = item.modified
                    )
                    HyperLinkText(
                        modifier = Modifier
                            .constrainAs(url) {
                                width = Dimension.fillToConstraints
                                top.linkTo(lastUpdate.bottom, 30.dp)
                                start.linkTo(lastUpdate.start)
                                end.linkTo(lastUpdate.end)
                            }
                            .clickable {
                                onNavigateWebScreen()
                            },
                        text = item.url,
                        linkColor = color_hyper_link_text
                    )
                }
            }

        }
    }

}