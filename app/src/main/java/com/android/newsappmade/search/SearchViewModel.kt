package com.android.newsappmade.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.android.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel(val newsUseCase: NewsUseCase): ViewModel() {
    suspend fun searchNews(q: String) = newsUseCase.searchNews(q).asLiveData()
}