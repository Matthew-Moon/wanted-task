package com.wanted.data.data.service

import com.wanted.data.data.dto.SearchCompany
import com.wanted.data.data.network.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("search/company")
    suspend fun getSearchCompany(
        @Query("query") query: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20,
    ): ResponseDto<SearchCompany>

}