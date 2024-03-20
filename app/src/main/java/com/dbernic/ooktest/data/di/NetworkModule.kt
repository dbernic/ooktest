package com.dbernic.ooktest.data.di

import android.util.Log
import com.dbernic.ooktest.BuildConfig
import com.dbernic.ooktest.data.rest.RestApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.InternalCoroutinesApi
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@InternalCoroutinesApi
object NetworkModule {

    private const val NETWORK_TAG = "OkHttp"
    private const val DEFAULT_TIMEOUT = 60L

    @Singleton
    @Provides
    fun providesApi(retrofit: Retrofit) : RestApi {
        return retrofit.create(RestApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideConvertorFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { log ->
            Log.d(NETWORK_TAG, log)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val connectionPool = ConnectionPool(5, 30, SECONDS)

        return OkHttpClient.Builder().apply {
            connectTimeout(DEFAULT_TIMEOUT, SECONDS)
            readTimeout(DEFAULT_TIMEOUT, SECONDS)
            writeTimeout(DEFAULT_TIMEOUT, SECONDS)
            addInterceptor(httpLoggingInterceptor)
            connectionPool(connectionPool)
            cache(null)
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient,
        gsonFactory: GsonConverterFactory,
    ): Retrofit.Builder {
        return Retrofit.Builder().apply {
            client(okHttpClient)
            addConverterFactory(gsonFactory)
        }
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().apply {
            setLenient()
            setPrettyPrinting()
        }.create()
    }

}