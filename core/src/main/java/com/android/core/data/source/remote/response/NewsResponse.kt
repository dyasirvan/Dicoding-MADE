package com.android.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles")
    val articles: List<ArticleResponse> = listOf()
)