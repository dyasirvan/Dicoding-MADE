package com.android.newsappmade.detail

import androidx.lifecycle.ViewModel
import com.android.core.domain.model.Article
import com.android.core.domain.usecase.NewsUseCase

class DetailNewsViewModel(private val newsUseCase: NewsUseCase): ViewModel() {
    fun setFavoriteTourism(article: Article, newStatus:Boolean) =
        newsUseCase.setFavoriteNews(article, newStatus)
}