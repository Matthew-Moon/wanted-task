package com.wanted.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanted.domain.model.CompanyAutoCompleteModel
import com.wanted.domain.model.CompanyModel
import com.wanted.domain.result.DomainResult
import com.wanted.domain.usecase.GetCompanyAutocompleteUseCase
import com.wanted.domain.usecase.GetCompanyListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(FlowPreview::class)
@HiltViewModel
class CompanyListViewModel @Inject constructor(
    private val getCompanyListUseCase: GetCompanyListUseCase,
    private val getCompanyAutocompleteUseCase: GetCompanyAutocompleteUseCase,
) : ViewModel() {

    // 검색어 상태
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // 진입 경로 상태 (자동완성/수동검색)
    private val _lastSearchType = MutableStateFlow<SearchType?>(null)
    val lastSearchType: StateFlow<SearchType?> = _lastSearchType.asStateFlow()

    // UI 상태
    private val _uiState = MutableStateFlow<CompanyListUiState>(CompanyListUiState.EmptyQuery)
    val uiState: StateFlow<CompanyListUiState> = _uiState.asStateFlow()

    // 페이징
    private var nextOffset: Int? = null
    private var autoCompleteJob: Job? = null

    init {
        // query 값이 바뀔 때마다 자동완성
        viewModelScope.launch {
            query
                .debounce(1000)
                .distinctUntilChanged()
                .collect { newQuery ->
                    if (newQuery.isBlank()) {
                        clearSearch()
                        return@collect
                    }
                    // 수동검색 도중엔 자동완성 차단
                    if (lastSearchType.value == SearchType.PASSIVITY) return@collect
                    autoSearchCompany(newQuery)
                }
        }
    }

    fun updateQuery(newValue: String) {
        _query.value = newValue
        _lastSearchType.value = SearchType.AUTOCOMPLETE
    }

    fun setLastSearchType(type: SearchType) {
        _lastSearchType.value = type
    }

    fun autoSearchCompany(newQuery: String) {
        autoCompleteJob?.cancel()
        autoCompleteJob = viewModelScope.launch {
            val trimmed = newQuery.trim()
            if (trimmed.isEmpty()) {
                clearSearch()
                return@launch
            }
            when (val result = getCompanyAutocompleteUseCase(trimmed)) {
                is DomainResult.Success -> {
                    _uiState.value = CompanyListUiState.Autocomplete(result.data)
                }

                is DomainResult.Failure -> {
                    _uiState.value = CompanyListUiState.Error(result.error.message)
                }
            }
        }
    }

    fun searchCompany(newQuery: String) {
        autoCompleteJob?.cancel()
        val trimmed = newQuery.trim()
        _lastSearchType.value = SearchType.PASSIVITY
        _query.value = trimmed

        if (trimmed.isEmpty()) {
            nextOffset = null
            _uiState.value = CompanyListUiState.EmptyQuery
            return
        }

        _uiState.value = CompanyListUiState.Loading(SearchType.PASSIVITY)
        nextOffset = null
        loadCompanyList(offset = 0, isNewSearch = true, type = SearchType.PASSIVITY)
    }

    fun loadNextPage() {
        val offset = nextOffset ?: return
        val current = _uiState.value
        if (current is CompanyListUiState.Success && !current.isAppending) {
            loadCompanyList(offset = offset, isNewSearch = false, type = current.type)
        }
    }

    private fun loadCompanyList(offset: Int, isNewSearch: Boolean, type: SearchType) {
        viewModelScope.launch {
            if (isNewSearch) {
                _uiState.value = CompanyListUiState.Loading(type)
            } else {
                val current = _uiState.value as? CompanyListUiState.Success
                if (current != null) {
                    _uiState.value = current.copy(isAppending = true)
                }
            }
            when (val result = getCompanyListUseCase(_query.value, offset, 20)) {
                is DomainResult.Success -> {
                    val paged = result.data
                    nextOffset = paged.nextOffset
                    val updatedList = if (isNewSearch) {
                        paged.companies
                    } else {
                        val current = (_uiState.value as? CompanyListUiState.Success)?.companies.orEmpty()
                        current + paged.companies
                    }
                    _uiState.value = CompanyListUiState.Success(
                        companies = updatedList,
                        nextOffset = paged.nextOffset,
                        isAppending = false,
                        type = type
                    )
                }

                is DomainResult.Failure -> {
                    _uiState.value = CompanyListUiState.Error(result.error.message)
                }
            }
        }
    }

    private fun clearSearch() {
        nextOffset = null
        _uiState.value = CompanyListUiState.EmptyQuery
    }

}


sealed interface CompanyListUiState {
    object EmptyQuery : CompanyListUiState
    data class Loading(val type: SearchType) : CompanyListUiState
    data class Success(
        val companies: List<CompanyModel>,
        val nextOffset: Int?,
        val isAppending: Boolean,
        val type: SearchType
    ) : CompanyListUiState

    data class Autocomplete(val results: List<CompanyAutoCompleteModel>) : CompanyListUiState
    data class Error(val message: String) : CompanyListUiState
}

enum class SearchType {
    PASSIVITY, AUTOCOMPLETE
}
