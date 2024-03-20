package com.dbernic.ooktest.data.di

import com.dbernic.ooktest.data.repository.RestRepository
import com.dbernic.ooktest.data.rest.RestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRestRepository(restApi: RestApi) : RestRepository = RestRepository(restApi)


}