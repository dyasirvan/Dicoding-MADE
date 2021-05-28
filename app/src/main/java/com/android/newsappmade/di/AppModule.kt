package com.android.newsappmade.di

import com.android.newsappmade.core.domain.usecase.NewsInteractor
import com.android.newsappmade.core.domain.usecase.NewsUseCase
import com.android.newsappmade.detail.DetailNewsViewModel
import com.android.newsappmade.favorit.FavoriteViewModel
import com.android.newsappmade.home.HomeViewModel
import com.android.newsappmade.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailNewsViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}