package com.android.newsappmade.core.data.source.remote.network

import com.android.newsappmade.core.data.source.remote.response.ArticleResponse
import com.android.newsappmade.core.data.source.remote.response.NewsResponse
import com.android.newsappmade.core.utils.Constant.Companion.API_KEY
import kotlinx.coroutines.flow.Flow
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
    fun searchNews(
        @Query("q")
        querySearch: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Flow<List<ArticleResponse>>

}