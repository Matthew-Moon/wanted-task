package com.wanted.task.data.repository

import com.wanted.task.data.dto.SearchCompany
import com.wanted.task.data.dto.toDomain
import com.wanted.task.data.network.safeApiCall
import com.wanted.task.data.network.toResult
import com.wanted.task.data.service.SearchApiService
import com.wanted.task.domain.model.CompanyModel
import com.wanted.task.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : SearchRepository {

    override suspend fun getSearchCompany(query: String, offset: Int, limit: Long): Result<List<CompanyModel>> =
        safeApiCall {
            searchApiService.getSearchCompany(
                query = query,
                offset = offset,
                limit = limit
            )
        }.toResult { it.toDomain() }
}