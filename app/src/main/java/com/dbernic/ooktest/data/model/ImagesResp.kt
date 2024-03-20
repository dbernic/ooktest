package com.dbernic.ooktest.data.model

import com.google.gson.annotations.SerializedName


data class Data(
    @SerializedName("postcards") val postcards: List<Postcard>,
    @SerializedName("totalPages") val pages: Int
)

data class Postcard(
    @SerializedName("image") var image: String
)
