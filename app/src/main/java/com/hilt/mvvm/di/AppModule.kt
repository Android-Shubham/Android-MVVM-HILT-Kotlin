package com.hilt.mvvm.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hilt.mvvm.data.remote.ApiService
import com.hilt.mvvm.data.remote.DataSource
import com.hilt.mvvm.data.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(ApiService: ApiService) =
        DataSource(ApiService)


    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: DataSource) =
        DataRepository(remoteDataSource)
}