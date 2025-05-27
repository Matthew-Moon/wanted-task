package com.wanted.data.data.network

import com.wanted.domain.result.ErrorBody
import com.wanted.data.data.dto.ApiResult
import org.json.JSONObject
import retrofit2.Response

typealias ResponseDto<T> = Response<T>

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                ApiResult.Success(it)
            } ?: ApiResult.Failure(ErrorBody(null, "Empty response body"))
        } else {
            val errorBody = response.errorBody()?.string()
            val apiError = parseApiError(errorBody)
            ApiResult.Failure(apiError)
        }
    } catch (e: Exception) {
        ApiResult.Failure(ErrorBody(null, e.localizedMessage ?: "Unknown error"))
    }
}

fun parseApiError(errorBody: String?): ErrorBody {
    return try {
        if (errorBody == null) return ErrorBody(null, "No error body")

        val json = JSONObject(errorBody)
        val rawCode = json.optString("error_code", "")
        val code = if (rawCode.isBlank()) null else rawCode

        val message = json.optString("message", "Unknown server error")

        ErrorBody(code, message)
    } catch (e: Exception) {
        e.printStackTrace()
        ErrorBody(null, "Malformed error response")
    }
}