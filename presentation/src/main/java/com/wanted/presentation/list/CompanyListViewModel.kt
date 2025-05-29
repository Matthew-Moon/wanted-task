package com.wanted.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanted.domain.model.CompanyModel
import com.wanted.domain.result.DomainResult
import com.wanted.domain.usecase.GetCompanyListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyListViewModel @Inject constructor(
    private val getCompanyListUseCase: GetCompanyListUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private var nextOffset: Int? = null

    private val _uiState = MutableStateFlow<CompanyListUiState>(CompanyListUiState.EmptyQuery)
    val uiState: StateFlow<CompanyListUiState> = _uiState.asStateFlow()

    fun searchCompany(newQuery: String) {
        _query.value = newQuery.trim()

        if (_query.value.isEmpty()) {
            nextOffset = null
            _uiState.value = CompanyListUiState.EmptyQuery
            return
        }

        if (_query.value.isBlank()) {
            nextOffset = null
            _uiState.value = CompanyListUiState.EmptyQuery
            return
        }

        nextOffset = null
        _uiState.value = CompanyListUiState.Loading
        loadCompanyList(offset = 0, isNewSearch = true)
    }

    fun loadNextPage() {
        val offset = nextOffset ?: return
        if (_uiState.value is CompanyListUiState.Loading) return
        loadCompanyList(offset = offset, isNewSearch = false)
    }

    private fun loadCompanyList(offset: Int, isNewSearch: Boolean) {
        viewModelScope.launch {
            if (isNewSearch) {
                _uiState.value = CompanyListUiState.Loading
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
                        val current = (_uiState.value as? CompanyListUiState.Success)?.companies ?: emptyList()
                        current + paged.companies
                    }

                    _uiState.value = CompanyListUiState.Success(
                        companies = updatedList,
                        nextOffset = paged.nextOffset,
                        isAppending = false
                    )
                }

                is DomainResult.Failure -> {
                    _uiState.value = CompanyListUiState.Error(result.error.message)
                }
            }
        }
    }
}

sealed interface CompanyListUiState {
    object Loading : CompanyListUiState
    data class Success(val companies: List<CompanyModel>, val nextOffset: Int?, val isAppending: Boolean = false) : CompanyListUiState
    data class Error(val message: String) : CompanyListUiState
    object EmptyQuery : CompanyListUiState
}
