package com.android.newsappmade.favorite.di

import com.android.newsappmade.favorite.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module{
    viewModel{ FavoriteViewModel(get()) }
}