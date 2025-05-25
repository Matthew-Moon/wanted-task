package com.wanted.task.data.dto

data class ApiError(
    val errorCode: String?,
    val message: String
)

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T): ApiResult<T>()
    data class Failure(val error: ApiError): ApiResult<Nothing>()
}
