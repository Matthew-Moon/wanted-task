package com.wanted.data.data.repository

import com.wanted.domain.model.PagedCompanyModel
import com.wanted.domain.repository.SearchRepository
import com.wanted.data.data.dto.toDomain
import com.wanted.data.data.network.safeApiCall
import com.wanted.domain.result.DomainResult
import com.wanted.data.data.mapper.toDomainResult

import com.wanted.data.data.service.SearchApiService
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : SearchRepository {

    override suspend fun getSearchCompany(query: String, offset: Int, limit: Int): DomainResult<PagedCompanyModel> =
        safeApiCall {
            searchApiService.getSearchCompany(
                query = query,
                offset = offset,
                limit = limit
            )
        }.toDomainResult { dto -> dto.toDomain() }
}