package com.picpay.desafio.android.util

import com.picpay.desafio.android.data.Constants
import com.picpay.desafio.android.domain.model.AsyncResult
import retrofit2.Response

fun <T: Any?> Response<T>.toAsyncResult(): AsyncResult<T>{
    return runCatching {
        if (isSuccessful) {
            AsyncResult.Success(body())
        } else {
            AsyncResult.Error(Constants.GENERIC_ERROR_MESSAGE)
        }
    }.getOrElse {
        AsyncResult.Error("${it.message}")
    }
}