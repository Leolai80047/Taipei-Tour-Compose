package com.leodemo.taipei_tour_compose.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.leodemo.taipei_tour_compose.R
import com.leodemo.taipei_tour_compose.presentation.utils.SupportLanguageEnum
import com.leodemo.taipei_tour_compose.ui.theme.color_top_app_bar_container
import com.leodemo.taipei_tour_compose.ui.theme.color_top_app_bar_onContainer

@Composable
fun ChooseLanguageDialog(
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color_top_app_bar_container)
                    .wrapContentHeight(),
                text = stringResource(R.string.please_select_language),
                color = color_top_app_bar_onContainer,
                textAlign = TextAlign.Center
            )
            LazyColumn(
                modifier = Modifier.background(Color.White)
            ) {
                itemsIndexed(SupportLanguageEnum.getSupportLanguageList()) { index, language ->
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                onSelect(language)
                            },
                        text = language
                    )
                    if (index != SupportLanguageEnum.getSupportLanguageList().lastIndex) {
                        Divider(color = Color.Black, thickness = 1.dp)
                    }
                }
            }
        }
    }
}