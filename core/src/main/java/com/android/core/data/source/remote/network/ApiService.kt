package com.android.core.data.source.remote.network

import com.android.core.data.source.remote.response.NewsResponse
import com.android.core.utils.Constant.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q")
        querySearch: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse

}