package com.android.core.data

import com.android.core.data.source.local.LocalDataSource
import com.android.core.data.source.remote.RemoteDataSource
import com.android.core.data.source.remote.network.ApiResponse
import com.android.core.data.source.remote.response.ArticleResponse
import com.android.core.domain.model.Article
import com.android.core.domain.repository.INewsRepository
import com.android.core.utils.AppExecutor
import com.android.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutor
): INewsRepository {
    override fun getAllNews(): Flow<Resource<List<Article>>> =
        object : com.android.core.data.NetworkBoundResource<List<Article>, List<ArticleResponse>>() {
            override fun loadFromDB(): Flow<List<Article>> {
                return localDataSource.getAllNews().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Article>?): Boolean =
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<ArticleResponse>>> =
                remoteDataSource.getBreakingNews()

            override suspend fun saveCallResult(data: List<ArticleResponse>) {
                val newsList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertNews(newsList)
            }
        }.asFlow()

    override suspend fun searchNews(q: String): Flow<List<Article>> {
        return remoteDataSource.searchNews(q).map {
            if(it is ApiResponse.Success) DataMapper.mapResponsesToDomain(it.data)
            else listOf()
        }
    }

    override fun getFavoriteNews(): Flow<List<Article>> {
        return localDataSource.getFavoriteNews().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteNews(article: Article, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(article)
        appExecutors.diskIO().execute { localDataSource.setFavoriteNews(tourismEntity, state) }
    }
}