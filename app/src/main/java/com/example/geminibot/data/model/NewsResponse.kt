package com.example.geminibot.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("next_url")
	val nextUrl: String? = null,

	@field:SerializedName("results")
	val results: List<NewsItem?>? = null,

	@field:SerializedName("request_id")
	val requestId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Publisher(

	@field:SerializedName("favicon_url")
	val faviconUrl: String? = null,

	@field:SerializedName("logo_url")
	val logoUrl: String? = null,

	@field:SerializedName("homepage_url")
	val homepageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class NewsItem(

	@field:SerializedName("published_utc")
	val publishedUtc: String? = null,

	@field:SerializedName("keywords")
	val keywords: List<String?>? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("publisher")
	val publisher: Publisher? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("article_url")
	val articleUrl: String? = null,

	@field:SerializedName("tickers")
	val tickers: List<String?>? = null
)
