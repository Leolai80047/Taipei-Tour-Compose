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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.leodemo.taipei_tour_compose.domain.model.AttractionInfo

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.AttractionPager(
    pager: LazyPagingItems<AttractionInfo>,
    listState: LazyListState,
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
            val refreshState = rememberPullToRefreshState()
            if (refreshState.isRefreshing) {
                LaunchedEffect(Unit) {
                    pager.refresh()
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = 5.dp)
                    .nestedScroll(refreshState.nestedScrollConnection)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .testTag("AttractionList")
                        .padding(horizontal = 5.dp),
                    state = listState
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
                if (refreshState.progress > 0.5f) {
                    PullToRefreshContainer(
                        modifier = Modifier.align(Alignment.TopCenter),
                        state = refreshState
                    )
                }
            }
        }
    }
}