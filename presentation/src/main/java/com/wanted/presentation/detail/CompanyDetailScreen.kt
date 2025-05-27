package com.wanted.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.wanted.presentation.theme.Black


@Composable
fun CompanyDetailScreen(
    viewModel: CompanyDetailViewModel = hiltViewModel(),
    companyId: Int,
    navController: NavHostController
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val detail by viewModel.companyDetail.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCompanyDetail(companyId.toLong())
    }


    when {
        isLoading -> {
            CircularProgressIndicator()
        }

        error != null -> {
            Text("오류 발생: $error", color = Color.Red)
        }

        detail != null -> {
            detail?.let { model ->
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
                            fontWeight = FontWeight.ExtraBold,
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
                            fontWeight = FontWeight.Bold,
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
                            fontWeight = FontWeight.Normal,
                            color = Black
                        )
                    )


                    if (model.link.isNotBlank()) {
                        HyperlinkText(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            title = "회사 홈페이지",
                            url = model.link
                        )
                    }

                    if (model.url.isNotBlank()) {
                        HyperlinkText(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            title = "원티드 바로가기",
                            url = model.url
                        )
                    }

                    Spacer(modifier = Modifier.height(50.dp))
                }
            }

        }
    }
}