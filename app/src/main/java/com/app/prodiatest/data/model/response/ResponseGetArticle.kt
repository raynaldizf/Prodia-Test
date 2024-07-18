package com.app.prodiatest.data.model.response


import com.google.gson.annotations.SerializedName

data class ResponseGetArticle(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("results")
    val resultGetArticles: List<ResultGetArticle>
)