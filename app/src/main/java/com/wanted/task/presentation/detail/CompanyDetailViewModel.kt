package com.wanted.task.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanted.task.data.dto.ApiResult
import com.wanted.task.domain.model.CompanyInfoModel
import com.wanted.task.domain.usecase.GetCompanyDetailUseCase
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _companyDetail = MutableStateFlow<CompanyInfoModel?>(null)
    val companyDetail: StateFlow<CompanyInfoModel?> = _companyDetail.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadCompanyDetail(companyId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            when (val result = getCompanyDetailUseCase(companyId)) {
                is ApiResult.Success -> {
                    _companyDetail.value = result.data
                    Timber.d("Company detail loaded: ${result.data}")
                }

                is ApiResult.Failure -> {
                    Timber.e("API Error: ${result.error.message}")
                    _errorMessage.value = result.error.message
                }
            }

            _isLoading.value = false
        }
    }


}
