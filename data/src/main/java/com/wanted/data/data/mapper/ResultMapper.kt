package com.wanted.data.data.mapper

import com.wanted.domain.result.DomainResult
import com.wanted.data.data.dto.ApiResult

fun <T, R> ApiResult<T>.toDomainResult(map: (T) -> R): DomainResult<R> {
    return when (this) {
        is ApiResult.Success -> DomainResult.Success(map(this.data))
        is ApiResult.Failure -> DomainResult.Failure(this.error)
    }
}
