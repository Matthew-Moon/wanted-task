package com.wanted.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.wanted.domain.model.CompanyAutoCompleteModel
import com.wanted.presentation.R
import com.wanted.presentation.theme.Black
import com.wanted.presentation.theme.White

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AutocompleteCard(
    model: CompanyAutoCompleteModel,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(White)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (model.logoImage.isNotBlank()) {
            GlideImage(
                model = model.logoImage,
                contentDescription = "회사 로고",
                modifier = Modifier.size(16.dp),
                failure = placeholder(R.drawable.ic_default_logo),
                loading = placeholder(R.drawable.ic_default_logo)
            )
        }
        Text(
            text = model.name,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.wantedsans_medium)),
                color = Black
            )
        )
    }
}
