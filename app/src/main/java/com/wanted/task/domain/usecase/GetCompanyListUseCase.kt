package com.wanted.task.domain.usecase

import com.wanted.task.data.dto.ApiResult
import com.wanted.task.domain.model.PagedCompanyModel
import com.wanted.task.domain.repository.SearchRepository
import javax.inject.Inject

class GetCompanyListUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String, offset: Int, limit: Long): ApiResult<PagedCompanyModel> {
        return searchRepository.getSearchCompany(
            query = query,
            offset = offset,
            limit = limit
        )
    }
}