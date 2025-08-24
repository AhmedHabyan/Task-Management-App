package com.example.taskmanagement.data.utils

sealed class ApiResult<out E> {
    object Loading:ApiResult<Nothing>()
    data class Success<T>(val response:T):ApiResult<T>()
    data class Failed(val error:Exception):ApiResult<Nothing>()
}