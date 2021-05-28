package com.android.newsappmade.core.data

import android.util.Log
import androidx.lifecycle.asLiveData
import com.android.newsappmade.core.data.source.local.LocalDataSource
import com.android.newsappmade.core.data.source.remote.RemoteDataSource
import com.android.newsappmade.core.data.source.remote.network.ApiResponse
import com.android.newsappmade.core.data.source.remote.response.ArticleResponse
import com.android.newsappmade.core.domain.model.Article
import com.android.newsappmade.core.domain.repository.INewsRepository
import com.android.newsappmade.core.utils.AppExecutor
import com.android.newsappmade.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import retrofit2.Response

class NewsRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutor
): INewsRepository {
    override fun getAllNews(): Flow<Resource<List<Article>>> =
        object : NetworkBoundResource<List<Article>, List<ArticleResponse>>() {
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
            DataMapper.mapResponsesToDomain(it)
        }
    }
        /*
        object : NetworkBoundResource<List<Article>, List<ArticleResponse>>() {
            override fun loadFromDB(): Flow<List<Article>> {
                return flowOf(listOf())
            }

            override fun shouldFetch(data: List<Article>?): Boolean =
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<ArticleResponse>>> {
                Log.d("RemoteDataSource", "createCall: $q ")
                return remoteDataSource.searchNews(q)
            }

            override suspend fun saveCallResult(data: List<ArticleResponse>) {

            }
        }.asFlow()

         */


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