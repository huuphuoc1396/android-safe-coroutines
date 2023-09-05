package com.example.androidsafecoroutines.di

import android.content.Context
import com.example.androidsafecoroutines.BuildConfig
import com.example.androidsafecoroutines.data.remote.ApiService
import com.example.androidsafecoroutines.data.remote.builder.DefaultRetrofitBuilder
import com.example.androidsafecoroutines.data.remote.interceptor.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun provideHereApiService(
        @ApplicationContext
        context: Context,
        retrofitBuilder: DefaultRetrofitBuilder,
        headerInterceptor: HeaderInterceptor,
    ): ApiService {
        return retrofitBuilder
            .baseUrl("https://api.github.com")
            .addInterceptor(headerInterceptor)
            .enableChucker(context, BuildConfig.DEBUG)
            .enableLogging(BuildConfig.DEBUG)
            .build()
            .create(ApiService::class.java)
    }
}