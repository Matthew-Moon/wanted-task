package com.wanted.task.data.service

import com.wanted.task.data.dto.SearchCompany
import com.wanted.task.data.network.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("search/company")
    suspend fun getSearchCompany(
        @Query("query") query: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Long = 20,
    ): ResponseDto<SearchCompany>

}