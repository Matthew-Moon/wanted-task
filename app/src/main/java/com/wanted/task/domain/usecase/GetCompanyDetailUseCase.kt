package com.wanted.task.domain.usecase

import com.wanted.task.data.dto.ApiResult
import com.wanted.task.domain.model.CompanyInfoModel
import com.wanted.task.domain.repository.CompanyRepository
import javax.inject.Inject

class GetCompanyDetailUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
        suspend operator fun invoke(companyId: Long): ApiResult<CompanyInfoModel> {
        return companyRepository.getCompanyDetail(companyId = companyId)
    }
}