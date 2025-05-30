package com.wanted.data.data.repository

import com.wanted.data.data.dto.SearchCompanyAutoComplete
import com.wanted.domain.model.PagedCompanyModel
import com.wanted.domain.repository.SearchRepository
import com.wanted.data.data.dto.toDomain
import com.wanted.data.data.network.safeApiCall
import com.wanted.domain.result.DomainResult
import com.wanted.data.data.mapper.toDomainResult

import com.wanted.data.data.service.SearchApiService
import com.wanted.domain.model.CompanyAutoCompleteModel
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

    override suspend fun getSearchCompanyAutocomplete(query: String): DomainResult<List<CompanyAutoCompleteModel>> =
        safeApiCall {
            searchApiService.getSearchCompanyAutocomplete(query = query)
        }.toDomainResult { dto -> dto.data.map { it.toDomain() } }
}