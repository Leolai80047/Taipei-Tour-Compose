package com.leodemo.taipei_tour_compose.ui.components.attraction

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.leodemo.taipei_tour_compose.R
import com.leodemo.taipei_tour_compose.ui.utils.LocalizeContext
import com.leodemo.taipei_tour_compose.ui.utils.language.SupportLanguageEnum

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
                    .background(MaterialTheme.colorScheme.primary)
                    .wrapContentHeight(),
                text = LocalizeContext.current.getString(R.string.please_select_language),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
            LazyColumn(
                modifier = Modifier.background(Color.White)
            ) {
                itemsIndexed(SupportLanguageEnum.getSupportLanguageList()) { index, language ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                onSelect(language)
                            },
                        text = language
                    )
                    if (index != SupportLanguageEnum.getSupportLanguageList().lastIndex) {
                        HorizontalDivider(color = Color.Black, thickness = 1.dp)
                    }
                }
            }
        }
    }
}