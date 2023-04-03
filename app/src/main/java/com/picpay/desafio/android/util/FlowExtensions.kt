package com.picpay.desafio.android.util

import com.picpay.desafio.android.domain.model.AsyncResult
import kotlinx.coroutines.flow.*
import retrofit2.Response

fun <T: Any?> asyncResultFlow(request: suspend () -> Response<T>) = flow {
    emit(AsyncResult.Loading)
    emit(request().toAsyncResult())
}