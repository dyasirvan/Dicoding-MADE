package com.android.newsappmade

import android.app.Application
import com.android.core.di.databaseModule
import com.android.core.di.networkModule
import com.android.core.di.repositoryModule
import com.android.newsappmade.di.useCaseModule
import com.android.newsappmade.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}