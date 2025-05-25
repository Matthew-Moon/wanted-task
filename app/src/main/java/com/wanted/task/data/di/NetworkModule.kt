package com.wanted.task.data.di

import com.wanted.task.data.service.CompanyApiService
import com.wanted.task.data.service.SearchApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://openapi.wanted.jobs/v1/"
    private const val CLIENT_ID = "ocir8SVWIv03EUm6vCC7u2Rm"
    private const val CLIENT_SECRET = "dGaBNgY7M79X8jjKFq04XRnG4mr2y5pRRS1yDIM29NlOgiwL"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("wanted-client-id", CLIENT_ID)
                    .addHeader("wanted-client-secret", CLIENT_SECRET)
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchApiService(retrofit: Retrofit): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCompanyApiService(retrofit: Retrofit): CompanyApiService {
        return retrofit.create(CompanyApiService::class.java)
    }
}