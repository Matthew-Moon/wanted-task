package com.wanted.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.wanted.presentation.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CompanyLogoImage(
    modifier: Modifier = Modifier,
    model: Any?
) {
    GlideImage(
        model = model,
        contentDescription = "회사 로고",
        modifier = modifier,
        failure = placeholder(R.drawable.ic_default_logo),
        loading = placeholder(R.drawable.ic_default_logo)
    )
}