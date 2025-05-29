package com.wanted.domain.repository

import com.wanted.domain.model.PagedCompanyModel
import com.wanted.domain.result.DomainResult

interface SearchRepository {
    suspend fun getSearchCompany(query: String, offset: Int, limit: Int): DomainResult<PagedCompanyModel>
}