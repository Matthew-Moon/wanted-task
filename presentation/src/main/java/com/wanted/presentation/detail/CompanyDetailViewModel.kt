package com.wanted.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanted.domain.model.CompanyInfoModel
import com.wanted.domain.result.DomainResult
import com.wanted.domain.usecase.GetCompanyDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class CompanyDetailViewModel @Inject constructor(
    private val getCompanyDetailUseCase: GetCompanyDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CompanyDetailUiState>(CompanyDetailUiState.Idle)
    val uiState: StateFlow<CompanyDetailUiState> = _uiState.asStateFlow()

    fun loadCompanyDetail(companyId: Int) {
        viewModelScope.launch {
            _uiState.value = CompanyDetailUiState.Loading

            when (val result = getCompanyDetailUseCase(companyId)) {
                is DomainResult.Success -> {
                    Timber.d("Company detail loaded: ${result.data}")
                    _uiState.value = CompanyDetailUiState.Success(result.data)
                }

                is DomainResult.Failure -> {
                    Timber.e("API Error: ${result.error.message}")
                    _uiState.value = CompanyDetailUiState.Error(result.error.message)
                }
            }
        }
    }
}

sealed interface CompanyDetailUiState {
    object Idle : CompanyDetailUiState
    object Loading : CompanyDetailUiState
    data class Success(val company: CompanyInfoModel) : CompanyDetailUiState
    data class Error(val message: String) : CompanyDetailUiState
}

