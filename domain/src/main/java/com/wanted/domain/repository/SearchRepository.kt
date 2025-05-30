package com.wanted.domain.repository

import com.wanted.domain.model.CompanyAutoCompleteModel
import com.wanted.domain.model.PagedCompanyModel
import com.wanted.domain.result.DomainResult

interface SearchRepository {
    suspend fun getSearchCompany(query: String, offset: Int, limit: Int): DomainResult<PagedCompanyModel>
    suspend fun getSearchCompanyAutocomplete(query: String): DomainResult<List<CompanyAutoCompleteModel>>
}