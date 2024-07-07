package com.leodemo.taipei_tour_compose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.leodemo.taipei_tour.data.api.AttractionResponse
import com.leodemo.taipei_tour_compose.R
import com.leodemo.taipei_tour_compose.presentation.utils.SupportLanguageEnum
import com.leodemo.taipei_tour_compose.ui.components.AttractionPager
import com.leodemo.taipei_tour_compose.ui.main.MainViewModel
import com.leodemo.taipei_tour_compose.ui.theme.color_attraction_main_background
import com.leodemo.taipei_tour_compose.ui.theme.color_top_app_bar_container
import com.leodemo.taipei_tour_compose.ui.theme.color_top_app_bar_onContainer
import com.leodemo.taipei_tour_compose.ui.utils.ChooseLanguageDialog
import com.leodemo.taipei_tour_compose.ui.utils.LocalizeContext
import com.leodemo.taipei_tour_compose.ui.utils.dpToSp
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onLocaleChange: (Locale) -> Unit,
    onItemClick: (AttractionResponse.Data) -> Unit
) {
    val pager = viewModel.attractionPager.collectAsLazyPagingItems()
    var showDialog by remember {
        mutableStateOf(false)
    }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color_attraction_main_background)
        ) {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = color_top_app_bar_container
                ),
                title = {
                    Text(
                        text = LocalizeContext.current.getString(R.string.app_name),
                        color = color_top_app_bar_onContainer,
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
                onItemClick = onItemClick
            )
            if (showDialog) {
                ChooseLanguageDialog(
                    onDismiss = {
                        showDialog = false
                    },
                    onSelect = { language ->
                        val locale = SupportLanguageEnum.getLocale(language)
                        viewModel.currentLanguage = language
                        onLocaleChange(locale)
                        pager.refresh()
                        showDialog = false
                    }
                )
            }
        }
    }
}