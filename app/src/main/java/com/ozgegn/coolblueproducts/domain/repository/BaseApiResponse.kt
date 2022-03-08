package com.ozgegn.coolblueproducts.domain.repository

import com.ozgegn.coolblueproducts.util.BaseResult
import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): BaseResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return BaseResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): BaseResult<T> =
        BaseResult.Error("Api call failed $errorMessage")

}