package com.android.core.domain.repository

import com.android.core.data.Resource
import com.android.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface INewsRepository {

    fun getAllNews(): Flow<Resource<List<Article>>>

    suspend fun searchNews(q: String): Flow<List<Article>>

    fun getFavoriteNews(): Flow<List<Article>>

    fun setFavoriteNews(article: Article, state: Boolean)
}