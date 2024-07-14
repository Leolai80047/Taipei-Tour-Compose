package com.leodemo.taipei_tour_compose.ui.components.attraction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.leodemo.taipei_tour_compose.R
import com.leodemo.taipei_tour_compose.ui.utils.LocalizeContext
import com.leodemo.taipei_tour_compose.ui.utils.dpToSp

@Composable
fun AttractionAlertDialog(
    showDialog: Boolean,
    content: String,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(bottom = 30.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                        )
                        .wrapContentHeight(),
                    text = LocalizeContext.current.getString(R.string.system_error),
                    textAlign = TextAlign.Center,
                    fontSize = 20.dp.dpToSp(),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .wrapContentHeight(),
                    text = content,
                    textAlign = TextAlign.Center,
                    fontSize = 18.dp.dpToSp(),

                    )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 30.dp)
                        .background(
                            color = Color(0xFF8E929B),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .wrapContentHeight()
                        .clickable(
                            indication = null,
                            interactionSource = null
                        ) {
                            onDismiss()
                        },
                    text = LocalizeContext.current.getString(R.string.confirm),
                    textAlign = TextAlign.Center,
                    fontSize = 16.dp.dpToSp(),
                    color = Color.White
                )
            }
        }
    }
}