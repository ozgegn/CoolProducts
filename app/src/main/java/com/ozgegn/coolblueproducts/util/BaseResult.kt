package com.ozgegn.coolblueproducts.util

sealed class BaseResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : BaseResult<T>(data)
    class Error<T>(message: String, data: T? = null) : BaseResult<T>(data, message)
}