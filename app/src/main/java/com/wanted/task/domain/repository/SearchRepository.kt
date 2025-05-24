package com.wanted.task.domain.repository

import com.wanted.task.data.dto.ApiResult
import com.wanted.task.domain.model.PagedCompanyModel

interface SearchRepository {
    suspend fun getSearchCompany(query: String, offset: Int, limit: Long): ApiResult<PagedCompanyModel>
}