package com.wanted.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanted.domain.model.CompanyModel
import com.wanted.domain.usecase.GetCompanyListUseCase
import com.wanted.domain.result.DomainResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class CompanyListViewModel @Inject constructor(
    private val getCompanyListUseCase: GetCompanyListUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")

    private val _nextOffset = MutableStateFlow<Int?>(null)

    private val _companyList = MutableStateFlow<List<CompanyModel>>(emptyList())
    val companyList: StateFlow<List<CompanyModel>> = _companyList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun searchCompany(newQuery: String) {
        _query.value = newQuery

        if (newQuery.isBlank()) {
            _companyList.value = emptyList()
            _nextOffset.value = null
            return
        }

        _companyList.value = emptyList()
        _nextOffset.value = null
        loadCompanyList(0)
    }

    fun loadNextPage() {
        val offset = _nextOffset.value ?: return
        if (_isLoading.value) return
        loadCompanyList(offset)
    }

    private fun loadCompanyList(offset: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            when (val result = getCompanyListUseCase(_query.value, offset, 20L)) {
                is DomainResult.Success -> {
                    val paged = result.data
                    Timber.d("Success offset=${paged.nextOffset}, size=${paged.companies.size}")

                    _companyList.update { old ->
                        if (offset == 0) paged.companies else old + paged.companies
                    }

                    _nextOffset.value = paged.nextOffset
                }

                is DomainResult.Failure -> {
                    Timber.e("API Error: ${result.error.message}")
                }
            }

            _isLoading.value = false
        }
    }
}
