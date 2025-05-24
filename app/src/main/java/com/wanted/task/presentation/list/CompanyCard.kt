package com.wanted.task.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.task.presentation.theme.WantedBlack
import com.wanted.task.presentation.theme.WantedGrey


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CompanyCard(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = "https://static.wanted.co.kr/images/wdes/0_5.435127bb.jpg",
                contentDescription = "회사 로고",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = "원티드랩",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = WantedBlack
                )
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 10.dp)
                .padding(horizontal = 6.dp),
            text = "동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세, 동해물과 백두산이 마르고 닳도록",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                lineHeight = 20.sp,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = WantedGrey,
            )
        )

    }
}

@Preview
@Composable
fun CompanyCardPreview() {
    CompanyCard()
}