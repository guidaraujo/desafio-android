package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.model.AsyncResult
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repo.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactListUseCase @Inject constructor(private val repository: ContactRepository) {
    suspend operator fun invoke(): Flow<AsyncResult<List<User>>> = repository.getContactList()
}