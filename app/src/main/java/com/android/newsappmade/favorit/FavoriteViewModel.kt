package com.android.newsappmade.favorit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.android.core.domain.usecase.NewsUseCase

class FavoriteViewModel(newsUseCase: NewsUseCase) : ViewModel() {
    val favoriteNews = newsUseCase.getFavoriteNews().asLiveData()
}

