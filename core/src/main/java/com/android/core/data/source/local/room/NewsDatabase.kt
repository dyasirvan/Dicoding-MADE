package com.android.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.core.data.source.local.entity.ArticleEntity
import com.android.core.utils.SourceTypeConverter

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
@TypeConverters(SourceTypeConverter::class)

abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsDao
}