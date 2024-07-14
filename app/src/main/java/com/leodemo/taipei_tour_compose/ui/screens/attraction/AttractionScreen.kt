package com.leodemo.taipei_tour_compose.ui.screens.attraction

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.leodemo.taipei_tour_compose.R
import com.leodemo.taipei_tour_compose.domain.model.AttractionInfo
import com.leodemo.taipei_tour_compose.ui.components.attraction.AttractionPager
import com.leodemo.taipei_tour_compose.ui.components.attraction.ChooseLanguageDialog
import com.leodemo.taipei_tour_compose.ui.components.attraction.MoveTopButton
import com.leodemo.taipei_tour_compose.ui.utils.LocalizeContext
import com.leodemo.taipei_tour_compose.ui.utils.dpToSp
import com.leodemo.taipei_tour_compose.ui.utils.language.SupportLanguageEnum
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AttractionScreen(
    viewModel: AttractionViewModel = hiltViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    onLocaleChange: (Locale) -> Unit,
    onItemClick: (AttractionInfo) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pager = viewModel.attractionPager.collectAsLazyPagingItems()
    var showDialog by remember {
        mutableStateOf(false)
    }
    val listState = rememberLazyListState()
    val showScrollTop by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 5
        }
    }
    val scrollToTop: () -> Unit = {
        scope.launch {
            listState.scrollToItem(0)
        }
    }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
        ) {
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
                actions = {
                    Image(
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                showDialog = true
                            },
                        painter = painterResource(R.drawable.ic_translate),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            )
            AttractionPager(
                pager = pager,
                listState = listState,
                animatedVisibilityScope = animatedVisibilityScope,
                onItemClick = onItemClick
            )
            if (showDialog) {
                ChooseLanguageDialog(
                    onDismiss = {
                        showDialog = false
                    },
                    onSelect = { language ->
                        val locale = SupportLanguageEnum.getLocale(language)
                        onLocaleChange(locale)
                        viewModel.setLanguage(language)
                        pager.refresh()
                        scrollToTop()
                        showDialog = false
                    }
                )
            }
        }
        MoveTopButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 40.dp, bottom = 40.dp),
            visible = showScrollTop,
            onClick = scrollToTop
        )
    }
}