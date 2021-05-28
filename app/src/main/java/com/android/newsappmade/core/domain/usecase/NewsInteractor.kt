package com.android.newsappmade.core.domain.usecase

import com.android.newsappmade.core.data.Resource
import com.android.newsappmade.core.domain.model.Article
import com.android.newsappmade.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class NewsInteractor(private val newsRepository: INewsRepository): NewsUseCase {
    override fun getAllNews(): Flow<Resource<List<Article>>> = newsRepository.getAllNews()

    override suspend fun searchNews(q: String): Flow<List<Article>> {
        return newsRepository.searchNews(q)
    }

    override fun getFavoriteNews(): Flow<List<Article>> = newsRepository.getFavoriteNews()

    override fun setFavoriteNews(article: Article, state: Boolean) =
        newsRepository.setFavoriteNews(article, state)
}