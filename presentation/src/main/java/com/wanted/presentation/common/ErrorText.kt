package com.wanted.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.wanted.presentation.R
import com.wanted.presentation.theme.Black

@Composable
fun ErrorText(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "에러 발생: $message",
            color = Color.Red,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.wantedsans_medium)),
                color = Black
            )
        )
    }

}