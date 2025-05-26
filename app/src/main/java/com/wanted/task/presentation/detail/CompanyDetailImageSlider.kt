package com.wanted.task.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.task.R
import com.wanted.task.domain.model.ImageModel
import com.wanted.task.presentation.theme.WantedBlack
import com.wanted.task.presentation.theme.WantedGrey
import com.wanted.task.presentation.theme.WantedWhite

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CompanyImageSlider(
    images: List<ImageModel>,
    logoUrl: String,
    onBackClick: () -> Unit
) {
    if (images.isEmpty()) return

    val imageCount = images.size
    val isSingleImage = imageCount == 1

    val pagerState = rememberPagerState(
        initialPage = if (isSingleImage) 0 else Int.MAX_VALUE / 2,
        pageCount = { if (isSingleImage) 1 else Int.MAX_VALUE }
    )

    val currentImageIndex by remember {
        derivedStateOf {
            if (isSingleImage) 0 else pagerState.currentPage.floorMod(imageCount)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f) // 이미지 높이 고정
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.matchParentSize()
        ) { page ->
            val imageIndex = page.floorMod(images.size)

            GlideImage(
                model = images[imageIndex].thumb,
                contentDescription = "회사 이미지 $imageIndex",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 12.dp)
                .background(WantedBlack.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .width(42.dp)
                .height(24.dp), // 선택사항: 높이도 고정 가능
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${currentImageIndex + 1}/${images.size}",
                color = WantedWhite,
                fontSize = 12.sp
            )
        }

        // 왼쪽 상단: 뒤로가기 아이콘
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.common_ic_back_24),
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .clickable { onBackClick() },
            tint = WantedWhite
        )

        GlideImage(
            model = logoUrl,
            contentDescription = "회사 로고",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp)
                .offset(y = 30.dp)
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(0.5.dp, WantedGrey, RoundedCornerShape(12.dp))
                .background(Color.White)
        )
    }
}

/**
 * 정수를 다른 정수로 나눈 나머지를 양수로 반환하는 확장 함수
 */
fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}