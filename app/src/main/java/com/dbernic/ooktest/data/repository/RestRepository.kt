package com.dbernic.ooktest.data.repository

import com.dbernic.ooktest.data.model.MainResponse
import com.dbernic.ooktest.data.rest.RestApi
import com.google.gson.JsonArray
import com.google.gson.JsonElement

class RestRepository(private val api: RestApi) {
    companion object {
        const val LIMIT = 30
    }

    suspend fun getPostcards(page: Int): MainResponse? {
        return api.getPostcards(page = page, limit = LIMIT).body()
    }
}