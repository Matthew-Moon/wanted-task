package com.wanted.task.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.task.domain.model.ImageModel
import com.wanted.task.presentation.theme.WantedBlack
import com.wanted.task.presentation.theme.WantedGrey
import com.wanted.task.presentation.theme.WantedWhite

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CompanyImageSlider(
    images: List<ImageModel>,
    logoUrl: String,
) {
    if (images.isEmpty()) return

    val initialPage = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { Int.MAX_VALUE }
    )

    val currentImageIndex by remember {
        derivedStateOf {
            pagerState.currentPage.floorMod(images.size)
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

        // 페이지 인디케이터 - 이 Box 안에서 위치 기준이 맞춰짐
        Text(
            text = "${currentImageIndex + 1}/${images.size}",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 12.dp) // 여기서 패딩 조정
                .background(WantedBlack.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 2.dp),
            color = WantedWhite,
            fontSize = 12.sp
        )

        // 왼쪽 상단: 뒤로가기 아이콘
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .clickable { /* TODO: 뒤로가기 동작 */ }
                .padding(8.dp),
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