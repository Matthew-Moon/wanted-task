package com.wanted.task.domain.repository

import com.wanted.task.domain.model.CompanyModel

interface SearchRepository {
    suspend fun getSearchCompany(query: String, offset: Int, limit: Long): Result<List<CompanyModel>>
}