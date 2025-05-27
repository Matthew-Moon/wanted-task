package com.wanted.domain.repository

import com.wanted.domain.model.CompanyInfoModel
import com.wanted.domain.result.DomainResult

interface CompanyRepository {
        suspend fun getCompanyDetail(companyId: Long): DomainResult<CompanyInfoModel>
}