package com.wanted.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.wanted.domain.model.CompanyInfoModel
import com.wanted.domain.model.GeoLocationModel
import com.wanted.domain.model.ImageModel
import com.wanted.domain.model.LocationModel
import com.wanted.domain.model.LogoUrlModel
import com.wanted.domain.model.ViewportModel
import com.wanted.domain.model.ViewportPositionModel
import com.wanted.presentation.R
import com.wanted.presentation.theme.Black

@Composable
fun CompanyDetailContent(
    model: CompanyInfoModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CompanyImageSlider(
            images = model.images,
            logoUrl = model.logoUrl.thumb,
            onBackClick = { navController.popBackStack() }
        )

        Text(
            modifier = Modifier
                .padding(top = 42.dp)
                .padding(horizontal = 16.dp),
            text = model.name,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.wantedsans_extrabold)),
                color = Black
            )
        )

        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp),
            text = "기업 상세",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.wantedsans_bold)),
                color = Black
            )
        )

        DescriptionText(
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp),
            text = model.description,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.wantedsans_medium)),
                color = Black
            )
        )

        if (model.link.isNotBlank()) {
            HyperlinkText(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                title = "회사 홈페이지",
                url = model.link,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.wantedsans_regular)),
                    color = Black
                )
            )
        }

        if (model.url.isNotBlank()) {
            HyperlinkText(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                title = "원티드 바로가기",
                url = model.url,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.wantedsans_regular)),
                    color = Black
                )
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CompanyDetailContentPreview() {
    val dummyImage = ImageModel.placeholder()
    val dummyCompany = CompanyInfoModel(
        address = "서울특별시 강남구 테헤란로 427",
        companyConfirm = true,
        description = "원티드랩은 커리어 성장 플랫폼 '원티드'를 운영하며, 채용·커리어·학습 서비스를 제공합니다.",
        geoLocation = GeoLocationModel(
            location = LocationModel(lat = 37.5010, lng = 127.0396),
            locationType = "ROOFTOP",
            viewport = ViewportModel(
                northeast = ViewportPositionModel(37.5020, 127.0400),
                southwest = ViewportPositionModel(37.5000, 127.0380)
            )
        ),
        id = 1,
        images = listOf(dummyImage, dummyImage.copy(id = 2)),
        link = "https://www.wantedlab.com",
        logoUrl = LogoUrlModel(
            origin = "https://cdn.wanted.co.kr/logo/wantedlab.png",
            thumb = "https://cdn.wanted.co.kr/logo/wantedlab_thumb.png"
        ),
        name = "원티드랩",
        registrationNumber = "110111-1234567",
        url = "https://www.wanted.co.kr/company/wantedlab"
    )

    CompanyDetailContent(
        model = dummyCompany,
        navController = rememberNavController()
    )
}
