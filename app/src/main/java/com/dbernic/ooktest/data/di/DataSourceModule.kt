package com.dbernic.ooktest.data.di

import com.dbernic.ooktest.data.datasourse.PostcardsDataSource
import com.dbernic.ooktest.data.repository.RestRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun providesPostcardDataSource(restRepository: RestRepository, gson: Gson) : PostcardsDataSource =
        PostcardsDataSource(restRepository, gson)

}