package com.android.core.data.source.remote.network

import com.android.core.BuildConfig
import com.android.core.data.source.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q")
        querySearch: String,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): NewsResponse

}