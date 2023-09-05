package com.example.androidsafecoroutines.di

import com.example.androidsafecoroutines.data.repository.RepoRepository
import com.example.androidsafecoroutines.data.repository.RepoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsRepoRepository(
        userRepositoryImpl: RepoRepositoryImpl,
    ): RepoRepository
}
