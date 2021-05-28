package com.android.newsappmade.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.android.newsappmade.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel(val newsUseCase: NewsUseCase): ViewModel() {
    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val searchNews = queryChannel.asFlow()
        .debounce(1000)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            newsUseCase.searchNews(it)
        }
        .asLiveData()

//    suspend fun searchNews(q: String) = newsUseCase.searchNews(q).asLiveData()

}