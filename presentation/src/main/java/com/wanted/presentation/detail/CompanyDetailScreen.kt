package com.wanted.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wanted.presentation.common.ErrorText


@Composable
fun CompanyDetailScreen(
    viewModel: CompanyDetailViewModel = hiltViewModel(),
    navController: NavController,
    companyId: Int
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(companyId) {
        viewModel.loadCompanyDetail(companyId)
    }

    when (val state = uiState) {
        is CompanyDetailUiState.Idle -> {}
        is CompanyDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is CompanyDetailUiState.Success -> {
            CompanyDetailContent(model = state.company, navController = navController)
        }

        is CompanyDetailUiState.Error -> {
            ErrorText(state.message)
        }
    }
}
