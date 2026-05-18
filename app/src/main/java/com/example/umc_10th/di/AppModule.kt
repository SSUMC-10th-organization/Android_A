package com.example.umc_10th.di

import com.example.umc_10th.network.ReqResApi
import com.example.umc_10th.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideReqResApi(): ReqResApi = RetrofitClient.api
}
