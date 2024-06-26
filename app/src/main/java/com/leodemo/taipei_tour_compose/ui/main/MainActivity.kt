package com.leodemo.taipei_tour_compose.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.leodemo.taipei_tour_compose.R
import com.leodemo.taipei_tour_compose.ui.components.AttractionPager
import com.leodemo.taipei_tour_compose.ui.theme.AppTheme
import com.leodemo.taipei_tour_compose.ui.theme.color_top_app_bar_container
import com.leodemo.taipei_tour_compose.ui.utils.dpToSp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainScreen() {
        val pager = viewModel.attractionPager.collectAsLazyPagingItems()
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
                actions = {
                    Image(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(R.drawable.ic_translate),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            )
            AttractionPager(pager = pager)
        }
    }
}