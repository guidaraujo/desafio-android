package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.domain.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getContactList(): Response<List<User>>
}