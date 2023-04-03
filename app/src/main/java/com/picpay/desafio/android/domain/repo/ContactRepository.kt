package com.picpay.desafio.android.domain.repo

import com.picpay.desafio.android.domain.model.AsyncResult
import com.picpay.desafio.android.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun getContactList(): Flow<AsyncResult<List<User>>>
}