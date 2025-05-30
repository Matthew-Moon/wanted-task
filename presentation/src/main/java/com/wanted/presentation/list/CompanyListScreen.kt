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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wanted.presentation.common.ErrorText
import com.wanted.presentation.theme.Grey
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun CompanyListScreen(
    modifier: Modifier = Modifier,
    viewModel: CompanyListViewModel = hiltViewModel(),
    onCompanyClick: (Int) -> Unit
) {
    val query by viewModel.query.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val lastSearchType by viewModel.lastSearchType.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(query) {
        val isSearchInProgress = uiState is CompanyListUiState.Success || uiState is CompanyListUiState.Loading
        if (query.isNotBlank() && !isSearchInProgress) {
            viewModel.autoSearchCompany(query)
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { lastIndex ->
                val companies = (uiState as? CompanyListUiState.Success)?.companies.orEmpty()
                val isLoading = (uiState as? CompanyListUiState.Success)?.isAppending == true
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
                onValueChange = { viewModel.updateQuery(it) },
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
                                viewModel.updateQuery("")
                                viewModel.searchCompany("")
                            },
                        tint = Grey
                    )
                }
            )
        },
        content = { innerPadding ->
            when {
                // 자동완성 경로로 진입했으면 자동완성 결과 UI
                lastSearchType == SearchType.AUTOCOMPLETE && uiState is CompanyListUiState.Autocomplete -> {
                    val state = uiState as CompanyListUiState.Autocomplete
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        items(state.results) { autoItem ->
                            AutocompleteCard(
                                model = autoItem,
                                onClick = {
                                    // 자동완성 아이템 클릭시 자동완성 경로로 설정
                                    viewModel.setLastSearchType(SearchType.AUTOCOMPLETE)
                                    onCompanyClick(autoItem.companyId)
                                }
                            )
                        }
                    }
                }

                // 수동검색(엔터)로 진입했으면 리스트 결과 UI
                lastSearchType == SearchType.PASSIVITY && uiState is CompanyListUiState.Success -> {
                    val state = uiState as CompanyListUiState.Success
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

                // 로딩 화면
                uiState is CompanyListUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                // 에러 화면
                uiState is CompanyListUiState.Error -> {
                    val errorState = uiState as CompanyListUiState.Error
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorText(errorState.message)
                    }
                }

                else -> {}
            }
        }
    )
}