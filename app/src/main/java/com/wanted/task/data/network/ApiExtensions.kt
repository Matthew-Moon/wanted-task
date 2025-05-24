package com.wanted.task.data.network

import com.wanted.task.data.dto.ApiException
import com.wanted.task.data.dto.ApiResponse
import com.wanted.task.data.dto.ApiResult
import retrofit2.Response


typealias ResponseDto<T> = Response<ApiResponse<T>>

/**
 * API 호출에 대한 공통 처리 함수
 * - HTTP 오류, 응답 실패, 예외 상황을 모두 ApiResult로 매핑
 */
suspend inline fun <T> safeApiCall(
    crossinline call: suspend () -> Response<ApiResponse<T>>
): ApiResult<T> {
    return try {
        val response = call()

        val body = response.body()
        if (body != null) {
            if (body.success) {
                ApiResult.Success(body.data)
            } else {
                ApiResult.Failure(
                    code = body.error?.code ?: "UNKNOWN",
                    message = body.error?.message ?: "알 수 없는 오류"
                )
            }
        } else {
            ApiResult.Failure("EMPTY_BODY", "응답 본문이 비어 있습니다.")
        }
    } catch (e: Exception) {
        ApiResult.Exception(e)
    }
}

// 기본 버전: data != null 강제
inline fun <T, R> ApiResult<T>.toResult(transform: (T) -> R): Result<R> {
    return when (this) {
        is ApiResult.Success -> {
            val nonNullData = data ?: return Result.failure(
                NullPointerException("API 성공 응답이지만 data가 null입니다.")
            )
            Result.success(transform(nonNullData))
        }

        is ApiResult.Failure -> Result.failure(ApiException(code, raw, message))
        is ApiResult.Exception -> Result.failure(exception)
    }
}
