package com.wanted.data.data.dto

import com.wanted.domain.result.ErrorBody

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T): ApiResult<T>()
    data class Failure(val error: ErrorBody): ApiResult<Nothing>()
}
