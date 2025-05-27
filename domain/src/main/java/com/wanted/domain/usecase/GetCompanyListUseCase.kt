package com.wanted.domain.usecase

import com.wanted.domain.result.DomainResult
import com.wanted.domain.model.PagedCompanyModel
import com.wanted.domain.repository.SearchRepository
import javax.inject.Inject

class GetCompanyListUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String, offset: Int, limit: Long): DomainResult<PagedCompanyModel> {
        return searchRepository.getSearchCompany(
            query = query,
            offset = offset,
            limit = limit
        )
    }
}