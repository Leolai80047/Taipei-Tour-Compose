package com.leodemo.taipei_tour_compose.ui.components.attraction

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.leodemo.taipei_tour_compose.domain.model.AttractionInfo
import com.leodemo.taipei_tour_compose.ui.theme.color_attraction_main_background

@OptIn(ExperimentalMaterialApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AttractionPager(
    pager: LazyPagingItems<AttractionInfo>,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClick: (AttractionInfo) -> Unit
) {
    var showAlertDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(pager.loadState) {
        when {
            pager.loadState.refresh is LoadState.Error || pager.loadState.append is LoadState.Error -> {
                showAlertDialog = true
            }
        }
    }
    when {
        pager.loadState.refresh is LoadState.Loading -> {
            ShimmerAttractionItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(5.dp)
            )
        }

        pager.loadState.refresh is LoadState.Error -> {
            val error = (pager.loadState.refresh as LoadState.Error).error
            AttractionAlertDialog(showDialog = showAlertDialog, content = error.message ?: "") {
                showAlertDialog = false
            }
        }

        pager.loadState.append is LoadState.Error -> {
            val error = (pager.loadState.append as LoadState.Error).error
            AttractionAlertDialog(showDialog = showAlertDialog, content = error.message ?: "") {
                showAlertDialog = false
            }
        }

        else -> {
            var refreshing by remember {
                mutableStateOf(false)
            }
            val refreshState = rememberPullRefreshState(
                refreshing = refreshing,
                onRefresh = {
                    refreshing = true
                    pager.refresh()
                    refreshing = false
                })
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color_attraction_main_background)
                    .padding(horizontal = 5.dp)
                    .pullRefresh(refreshState)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)

                ) {
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    items(
                        count = pager.itemCount,
                        key = pager.itemKey(),
                        contentType = pager.itemContentType()
                    ) { index ->
                        pager.get(index)?.apply {
                            AttractionItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .clickable { onItemClick(this) },
                                animatedVisibilityScope = animatedVisibilityScope,
                                item = this
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    if (pager.loadState.append is LoadState.Loading) {
                        item {
                            ShimmerAttractionItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(5.dp)
                            )
                        }
                    }
                }
                PullRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    refreshing = refreshing,
                    state = refreshState
                )
            }
        }
    }
}