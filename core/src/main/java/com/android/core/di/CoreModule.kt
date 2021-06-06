package com.android.core.di

import androidx.room.Room
import com.android.core.data.NewsRepository
import com.android.core.data.source.local.LocalDataSource
import com.android.core.data.source.local.room.NewsDatabase
import com.android.core.data.source.remote.RemoteDataSource
import com.android.core.data.source.remote.network.ApiService
import com.android.core.domain.repository.INewsRepository
import com.android.core.utils.AppExecutor
import com.android.core.utils.Constant.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<NewsDatabase>().newsDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java, "News.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutor() }
    single<INewsRepository> { NewsRepository(get(), get(), get()) }
}