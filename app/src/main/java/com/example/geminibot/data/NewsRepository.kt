package com.example.geminibot.data

import com.example.geminibot.data.api.PolygonApi
import com.example.geminibot.data.model.NewsResponse

class NewsRepository(private val api: PolygonApi) {

    suspend fun fetchNews(apiKey: String): Result<NewsResponse> {
        return try {
            val response = api.getNews(apiKey = apiKey)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
