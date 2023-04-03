package com.picpay.desafio.android

import com.picpay.desafio.android.data.network.ApiService
import com.picpay.desafio.android.domain.model.User

class ExampleService(
    private val service: ApiService
) {

    fun example(): List<User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}