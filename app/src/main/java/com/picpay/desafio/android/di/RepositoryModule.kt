package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.repo.ContactRepositoryImpl
import com.picpay.desafio.android.domain.repo.ContactRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsContactRepository(
        repository: ContactRepositoryImpl
    ): ContactRepository
}