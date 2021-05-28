package com.android.newsappmade.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article (
    val id: Int = 0,
    val author: String? = "",
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val source: Source? = Source(),
    val title: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val isFavorit: Boolean
): Parcelable