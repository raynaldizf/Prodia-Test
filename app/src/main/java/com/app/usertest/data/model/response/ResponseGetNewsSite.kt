package com.app.prodiatest.data.model.response


import com.google.gson.annotations.SerializedName

data class ResponseGetNewsSite(
    @SerializedName("news_sites")
    val newsSites: List<String>,
    @SerializedName("version")
    val version: String
)