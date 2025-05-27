package com.wanted.data.data.di

import com.wanted.domain.repository.CompanyRepository
import com.wanted.domain.repository.SearchRepository
import com.wanted.data.data.repository.CompanyRepositoryImpl
import com.wanted.data.data.repository.SearchRepositoryImpl
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