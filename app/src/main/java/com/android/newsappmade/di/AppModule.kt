package com.android.newsappmade.di

import com.android.core.domain.usecase.NewsInteractor
import com.android.core.domain.usecase.NewsUseCase
import com.android.newsappmade.detail.DetailNewsViewModel
import com.android.newsappmade.home.HomeViewModel
import com.android.newsappmade.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailNewsViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}