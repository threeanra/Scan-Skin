package com.tearsdr0p.scanskin.data.network.retrofit

import com.tearsdr0p.scanskin.data.network.response.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getTopArticles(
        @Query("q") query: String,
    ): Response<ArticleResponse>

    @GET("everything")
    suspend fun getArticles(
        @Query("q") query: String,
    ): Response<ArticleResponse>
}