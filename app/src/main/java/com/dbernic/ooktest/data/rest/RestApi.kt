package com.dbernic.ooktest.data.rest

import com.dbernic.ooktest.BuildConfig
import com.dbernic.ooktest.data.model.MainResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RestApi {

    @GET("postcards/page/home")
    suspend fun getPostcards(
        @Header("Token") token: String = BuildConfig.API_KEY,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<MainResponse>

}