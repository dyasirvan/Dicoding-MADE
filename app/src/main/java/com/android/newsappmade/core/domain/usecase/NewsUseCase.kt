package com.android.newsappmade.core.domain.usecase

import com.android.newsappmade.core.data.Resource
import com.android.newsappmade.core.domain.model.Article
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getAllNews(): Flow<Resource<List<Article>>>
    suspend fun searchNews(q: String): Flow<List<Article>>
    fun getFavoriteNews(): Flow<List<Article>>
    fun setFavoriteNews(article: Article, state: Boolean)
}