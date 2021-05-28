package com.android.newsappmade.core.data.source.local

import com.android.newsappmade.core.data.source.local.entity.ArticleEntity
import com.android.newsappmade.core.data.source.local.room.NewsDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val newsDao: NewsDao) {
    fun getAllNews(): Flow<List<ArticleEntity>> = newsDao.getAllNews()

    fun getFavoriteNews(): Flow<List<ArticleEntity>> = newsDao.getFavoriteNews()

    suspend fun insertNews(newsList: List<ArticleEntity>) = newsDao.insertNews(newsList)

    fun setFavoriteNews(article: ArticleEntity, newState: Boolean) {
        article.isFavorite = newState
        newsDao.updateFavoriteNews(article)
    }
}