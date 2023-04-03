package com.picpay.desafio.android.data.repo

import com.picpay.desafio.android.data.network.ApiService
import com.picpay.desafio.android.domain.repo.ContactRepository
import com.picpay.desafio.android.util.asyncResultFlow
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val service: ApiService
): ContactRepository {
    override suspend fun getContactList() = asyncResultFlow {
        service.getContactList()
    }
}