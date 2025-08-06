package com.example.geminibot.data.api

import com.example.geminibot.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PolygonApi {

    @GET("v2/reference/news")
    suspend fun getNews(
        @Query("order") order: String = "asc",
        @Query("limit") limit: Int = 10,
        @Query("sort") sort: String = "published_utc",
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>
}
