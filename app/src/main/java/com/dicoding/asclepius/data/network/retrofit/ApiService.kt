package com.dicoding.asclepius.data.network.retrofit

import com.dicoding.asclepius.data.network.response.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getArticles(
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("language") language: String
    ): Call<ArticleResponse>
}