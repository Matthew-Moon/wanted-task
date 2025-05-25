package com.wanted.task.data.repository

import com.wanted.task.data.dto.ApiResult
import com.wanted.task.data.dto.toDomain
import com.wanted.task.data.network.safeApiCall
import com.wanted.task.data.network.toResult
import com.wanted.task.data.service.CompanyApiService
import com.wanted.task.domain.model.CompanyInfoModel
import com.wanted.task.domain.repository.CompanyRepository
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyApiService: CompanyApiService
) : CompanyRepository {

    override suspend fun getCompanyDetail(companyId: Long): ApiResult<CompanyInfoModel> =
        safeApiCall {
            companyApiService.getCompanyDetail(
                companyId = companyId,
            )
        }.toResult { dto -> dto.toDomain() }

}