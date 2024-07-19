package com.app.prodiatest.data.network

import com.app.prodiatest.data.model.response.ResponseGetArticle
import com.app.prodiatest.data.model.response.ResponseGetDetailArticle
import com.app.prodiatest.data.model.response.ResponseGetNewsSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("articles")
    suspend fun getArticles(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("title_contains") titleContains: String? = null,
        @Query("news_site") newsSite: String? = null
    ): ResponseGetArticle

    @POST("articles/{id}")
    fun getDetailArticle(
        @Path("id") id: String
    ): Call<ResponseGetDetailArticle>

    @GET("info")
    fun getNewsSite() : Call<ResponseGetNewsSite>
}