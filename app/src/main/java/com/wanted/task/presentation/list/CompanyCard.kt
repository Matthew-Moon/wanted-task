package com.wanted.task.presentation.list

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.wanted.task.domain.model.CompanyModel
import com.wanted.task.presentation.theme.WantedBlack
import com.wanted.task.presentation.theme.WantedGrey


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CompanyCard(
    modifier: Modifier = Modifier,
    company: CompanyModel
) {
    val processedText = remember { flattenLineBreaks(company.description) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
//                model = company.logoImg?.origin, TODO 기본이미지
                model = company.logoImg?.thumb,
                contentDescription = "회사 로고",
                modifier = Modifier
                    .size(52.dp)
                    .border(0.5.dp, WantedGrey, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp)),

                contentScale = ContentScale.Crop,
                // TODO
//                loading = {},
//                failure = {}
            )
            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = company.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = WantedBlack
                )
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 6.dp),
            text = processedText,
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

fun flattenLineBreaks(text: String): String {
    return text
        .replace("\n", " ")         // 모든 줄바꿈을 공백으로
        .replace("\\s+".toRegex(), " ") // 여러 공백은 하나로
        .trim()
}

@Preview
@Composable
fun CompanyCardPreview() {
//    CompanyCard()
}