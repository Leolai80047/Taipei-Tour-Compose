package com.leodemo.taipei_tour_compose.ui.screens

import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.leodemo.taipei_tour_compose.R
import com.leodemo.taipei_tour_compose.ui.main.MainViewModel
import com.leodemo.taipei_tour_compose.ui.theme.color_top_app_bar_container
import com.leodemo.taipei_tour_compose.ui.theme.color_top_app_bar_onContainer
import com.leodemo.taipei_tour_compose.ui.utils.LocalizeContext
import com.leodemo.taipei_tour_compose.ui.utils.dpToSp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    var showLoading by remember {
        mutableStateOf(true)
    }
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (toolbar, webView, progressBar) = createRefs()
        CenterAlignedTopAppBar(
            modifier = Modifier.constrainAs(toolbar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
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
            navigationIcon = {
                IconButton(onClick = {
                    onBack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        tint = color_top_app_bar_onContainer,
                        contentDescription = null
                    )
                }
            }
        )
        AndroidView(
            modifier = Modifier
                .constrainAs(webView) {
                    height = Dimension.fillToConstraints
                    top.linkTo(toolbar.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    clipToOutline = true

                    settings.apply {
                        useWideViewPort = true
                        loadWithOverviewMode = true
                        defaultTextEncodingName = "utf-8"
                        cacheMode = WebSettings.LOAD_NO_CACHE
                        loadWithOverviewMode = true
                        javaScriptEnabled = true
                        domStorageEnabled = true
                    }
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            if (request?.url != null) {
                                view?.loadUrl(request.url.toString())
                                return true
                            }
                            return false
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            showLoading = false
                        }
                    }
                }
            })
        {
            it.loadUrl(viewModel.currentItem.value?.url ?: "about:blank")
        }
        if (showLoading) {
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressBar) {
                    top.linkTo(toolbar.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                color = color_top_app_bar_container
            )
        }
    }
}