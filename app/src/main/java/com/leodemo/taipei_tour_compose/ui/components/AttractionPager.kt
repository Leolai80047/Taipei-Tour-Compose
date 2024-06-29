package com.leodemo.taipei_tour_compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.leodemo.taipei_tour.data.api.AttractionResponse
import com.leodemo.taipei_tour_compose.ui.theme.color_attraction_main_background

@Composable
fun AttractionPager(
    pager: LazyPagingItems<AttractionResponse.Data>,
    onItemClick: (AttractionResponse.Data) -> Unit
) {
    when(pager.loadState.refresh) {
        LoadState.Loading -> {}
        is LoadState.Error -> {}
        else -> {
            LazyColumn(
                modifier = Modifier
                    .background(color_attraction_main_background)
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
                            item = this
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}