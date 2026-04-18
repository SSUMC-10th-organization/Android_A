package com.example.umc_10th.di

import android.content.Context
import com.example.umc_10th.data.local.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideSharedPreferenceManager(
        @ApplicationContext context: Context
    ): SharedPreferenceManager {
        return SharedPreferenceManager(context)
    }
}