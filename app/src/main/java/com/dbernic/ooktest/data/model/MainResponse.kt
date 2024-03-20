package com.dbernic.ooktest.data.model

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class MainResponse (
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: JsonElement,
)