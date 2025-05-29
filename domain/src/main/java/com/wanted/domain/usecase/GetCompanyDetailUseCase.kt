package com.wanted.domain.usecase

import com.wanted.domain.result.DomainResult
import com.wanted.domain.model.CompanyInfoModel
import com.wanted.domain.repository.CompanyRepository
import javax.inject.Inject

class GetCompanyDetailUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
        suspend operator fun invoke(companyId: Int): DomainResult<CompanyInfoModel> {
        return companyRepository.getCompanyDetail(companyId = companyId)
    }
}