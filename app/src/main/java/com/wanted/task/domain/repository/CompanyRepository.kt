package com.wanted.task.domain.repository

import com.wanted.task.data.dto.ApiResult
import com.wanted.task.domain.model.CompanyInfoModel

interface CompanyRepository {
        suspend fun getCompanyDetail(companyId: Long): ApiResult<CompanyInfoModel>
}