package com.wanted.task.data.dto

// 공통 API 응답 모델
data class ApiResponse<T>(
    val success: Boolean,      // 성공 여부
    val data: T?,              // 응답 데이터 (없을 수 있음)
    val error: ApiError?       // 에러 정보 (없을 수 있음)
)

data class ApiError(
    val code: String,          // 에러 코드
    val message: String        // 에러 메시지
)

sealed class ApiResult<out T> {
    data class Success<T>(val data: T?) : ApiResult<T>()
    data class Failure(
        val code: String,
        val message: String,
        val raw: ErrorBody? = null
    ) : ApiResult<Nothing>()
    data class Exception(val exception: Throwable) : ApiResult<Nothing>()
}


data class ErrorBody(
    val code: String,
    val message: String
)

data class ApiException(
    val code: String,
    val raw: ErrorBody?,
    override val message: String
) : Throwable()
