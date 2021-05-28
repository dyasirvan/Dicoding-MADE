package com.android.newsappmade.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.android.newsappmade.core.domain.usecase.NewsUseCase

class HomeViewModel(newsUseCase: NewsUseCase): ViewModel() {
    val news = newsUseCase.getAllNews().asLiveData()
}