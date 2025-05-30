package com.wanted.domain.usecase

import com.wanted.domain.model.CompanyAutoCompleteModel
import com.wanted.domain.repository.SearchRepository
import com.wanted.domain.result.DomainResult
import javax.inject.Inject

class GetCompanyAutocompleteUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): DomainResult<List<CompanyAutoCompleteModel>> {
        return searchRepository.getSearchCompanyAutocomplete(
            query = query,
        )
    }
}