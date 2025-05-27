package com.wanted.domain.result

sealed class DomainResult<out T> {
    data class Success<out T>(val data: T) : DomainResult<T>()
    data class Failure(val error: ErrorBody) : DomainResult<Nothing>()
}

data class ErrorBody(
    val errorCode: String?,
    val message: String
)