package com.wanted.data.data.service

import com.wanted.data.data.dto.CompanyDetail
import com.wanted.data.data.network.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CompanyApiService {
    @GET("companies/{company_id}")
    suspend fun getCompanyDetail(
        @Path(value = "company_id", encoded = true) companyId: Long,
    ): ResponseDto<CompanyDetail>

}