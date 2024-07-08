package com.dicoding.asclepius.data.network.retrofit

import com.dicoding.asclepius.data.network.response.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getTopArticles(
        @Query("q") query: String,
    ): Response<ArticleResponse>

    @GET("everything")
    suspend fun getArticles(
        @Query("q") query: String,
    ): Response<ArticleResponse>
}