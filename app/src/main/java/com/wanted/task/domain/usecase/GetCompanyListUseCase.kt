package com.wanted.task.domain.usecase

import com.wanted.task.domain.model.CompanyModel
import com.wanted.task.domain.repository.SearchRepository
import javax.inject.Inject

class GetCompanyListUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String, offset: Int, limit: Long): Result<List<CompanyModel>> {
        return searchRepository.getSearchCompany(
            query = query,
            offset = offset,
            limit = limit
        )
    }
}