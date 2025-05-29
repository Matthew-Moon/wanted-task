package com.wanted.data.data.repository

import com.wanted.domain.model.CompanyInfoModel
import com.wanted.domain.repository.CompanyRepository
import com.wanted.domain.result.DomainResult
import com.wanted.data.data.dto.toDomain
import com.wanted.data.data.mapper.toDomainResult
import com.wanted.data.data.network.safeApiCall
import com.wanted.data.data.service.CompanyApiService
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyApiService: CompanyApiService
) : CompanyRepository {

    override suspend fun getCompanyDetail(companyId: Int): DomainResult<CompanyInfoModel> =
        safeApiCall {
            companyApiService.getCompanyDetail(
                companyId = companyId,
            )
        }.toDomainResult { dto -> dto.toDomain() }

}