package com.example.umc_10th.di

import com.example.umc_10th.data.repository.ProductLocalRepository
import com.example.umc_10th.data.repository.ProductLocalRepositoryImpl
import com.example.umc_10th.data.repository.UserRemoteRepository
import com.example.umc_10th.data.repository.UserRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductLocalRepository(
        impl: ProductLocalRepositoryImpl
    ): ProductLocalRepository

    @Binds
    @Singleton
    abstract fun bindUserRemoteRepository(
        impl: UserRemoteRepositoryImpl
    ): UserRemoteRepository
}
