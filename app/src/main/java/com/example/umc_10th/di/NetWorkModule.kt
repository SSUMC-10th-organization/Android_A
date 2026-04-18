package com.example.umc_10th.di

import com.example.umc_10th.data.remote.ReqResInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://reqres.in/" // 이드님이 사용하시는 베이스 URL

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideReqResInterface(retrofit: Retrofit): ReqResInterface {
        // 👈 Hilt에게 ReqResInterface 부품을 만드는 방법을 알려주는 핵심 코드!
        return retrofit.create(ReqResInterface::class.java)
    }
}