package com.picpay.desafio.android.domain.model

sealed class AsyncResult<out T: Any?> {
    data class Success<out T: Any>(val value: T? = null): AsyncResult<T>()
    data class Error(val message: String): AsyncResult<Nothing>()
    object Loading: AsyncResult<Nothing>()
}