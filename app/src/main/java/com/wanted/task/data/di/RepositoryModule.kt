package com.wanted.task.data.di

import com.wanted.task.data.repository.CompanyRepositoryImpl
import com.wanted.task.data.repository.SearchRepositoryImpl
import com.wanted.task.domain.repository.CompanyRepository
import com.wanted.task.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("Unused")
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        impl: SearchRepositoryImpl
    ): SearchRepository


    @Binds
    @Singleton
    abstract fun bindCompanyRepository(
        impl: CompanyRepositoryImpl
    ): CompanyRepository
}