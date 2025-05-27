package com.wanted.task.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.wanted.task.R
import com.wanted.task.domain.model.ImageModel
import com.wanted.task.presentation.theme.BorderGrey
import com.wanted.task.presentation.theme.Black
import com.wanted.task.presentation.theme.White

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CompanyImageSlider(
    images: List<ImageModel>,
    logoUrl: String,
    onBackClick: () -> Unit
) {
    val displayImages = remember(images) {
        if (images.isEmpty()) listOf(ImageModel.placeholder()) else images
    }

    val imageCount = displayImages.size

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { imageCount }
    )

    val currentImageIndex by remember {
        derivedStateOf { pagerState.currentPage }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.matchParentSize()
        ) { page ->
            val image = displayImages[page]
            Box(modifier = Modifier.fillMaxSize()) {
                GlideImage(
                    model = image.thumb,
                    contentDescription = "회사 이미지 $page",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // 상단 1/4 음영 처리 (선택 사항)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.25f)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.4f),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }
        }


        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 12.dp)
                .background(Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .width(42.dp)
                .height(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${currentImageIndex + 1}/$imageCount",
                color = White,
                fontSize = 12.sp
            )
        }

        // 뒤로가기 아이콘
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.common_ic_back_24),
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp)
                .statusBarsPadding()
                .clickable { onBackClick() },
            tint = White
        )

        // 회사 로고
        GlideImage(
            model = logoUrl,
            contentDescription = "회사 로고",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp)
                .offset(y = 30.dp)
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(0.5.dp, BorderGrey, RoundedCornerShape(12.dp))
                .background(Color.White),
            failure = placeholder(R.drawable.ic_default_logo),
            loading = placeholder(R.drawable.ic_default_logo)
        )
    }
}