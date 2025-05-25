package com.wanted.task.data.network

import com.wanted.task.data.dto.ApiError
import com.wanted.task.data.dto.ApiResult
import org.json.JSONObject
import retrofit2.Response

typealias ResponseDto<T> = Response<T>

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                ApiResult.Success(it)
            } ?: ApiResult.Failure(ApiError(null, "Empty response body"))
        } else {
            val errorBody = response.errorBody()?.string()
            val apiError = parseApiError(errorBody)
            ApiResult.Failure(apiError)
        }
    } catch (e: Exception) {
        ApiResult.Failure(ApiError(null, e.localizedMessage ?: "Unknown error"))
    }
}

fun parseApiError(errorBody: String?): ApiError {
    return try {
        if (errorBody == null) return ApiError(null, "No error body")

        val json = JSONObject(errorBody)
        val rawCode = json.optString("error_code", "")
        val code = if (rawCode.isBlank()) null else rawCode

        val message = json.optString("message", "Unknown server error")

        ApiError(code, message)
    } catch (e: Exception) {
        e.printStackTrace()
        ApiError(null, "Malformed error response")
    }
}

inline fun <T, R> ApiResult<T>.toResult(transform: (T) -> R): ApiResult<R> {
    return when (this) {
        is ApiResult.Success -> ApiResult.Success(transform(this.data))
        is ApiResult.Failure -> this
    }
}
