package com.android.core.data.source.local.room

import androidx.room.*
import com.android.core.data.source.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM articles")
    fun getAllNews(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles where isFavorite = 1")
    fun getFavoriteNews(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(article: List<ArticleEntity>)

    @Update
    fun updateFavoriteNews(article: ArticleEntity)
}