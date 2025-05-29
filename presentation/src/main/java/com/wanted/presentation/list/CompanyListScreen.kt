package com.wanted.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wanted.presentation.theme.Grey
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun CompanyListScreen(
    modifier: Modifier = Modifier,
    viewModel: CompanyListViewModel = hiltViewModel(),
    onCompanyClick: (Int) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    // 무한 스크롤 처리
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { lastIndex ->
                val companies = (uiState as? CompanyListUiState.Success)?.companies.orEmpty()
                val isLoading = (uiState as? CompanyListUiState.Success)?.isAppending ?: false

                if (lastIndex == companies.lastIndex && !isLoading) {
                    viewModel.loadNextPage()
                }
            }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        topBar = {
            CompanySearchBar(
                value = query,
                onValueChange = { query = it },
                onImeActionSearch = {
                    viewModel.searchCompany(query)
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "지우기",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                query = ""
                                viewModel.searchCompany("")
                            },
                        tint = Grey
                    )
                }
            )
        },
        content = { innerPadding ->
            when (val state = uiState) {

                CompanyListUiState.EmptyQuery -> Unit

                is CompanyListUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is CompanyListUiState.Success -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        items(state.companies) { company ->
                            CompanyCard(company = company) {
                                onCompanyClick(company.id)
                            }
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .padding(horizontal = 16.dp),
                                color = Color(0xFFE0E0E0),
                                thickness = 1.dp
                            )
                        }

                        if (state.isAppending) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }

                is CompanyListUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("에러 발생: ${state.message}", color = Color.Red)
                    }
                }
            }
        }
    )
}
