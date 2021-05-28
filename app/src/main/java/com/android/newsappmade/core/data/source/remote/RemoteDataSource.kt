package com.android.newsappmade.core.data.source.remote

import android.util.Log
import com.android.newsappmade.core.data.source.local.entity.ArticleEntity
import com.android.newsappmade.core.data.source.remote.network.ApiResponse
import com.android.newsappmade.core.data.source.remote.network.ApiService
import com.android.newsappmade.core.data.source.remote.response.ArticleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getBreakingNews(): Flow<ApiResponse<List<ArticleResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getBreakingNews()
                val dataArray = response.articles
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.articles))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
/*
    suspend fun searchNews(q: String): Flow<ApiResponse<List<ArticleResponse>>> {
        return flow {
            try {
                val response = apiService.searchNews(querySearch = q)
                val dataArray = response.articles
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.articles))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

 */


    suspend fun searchNews(q: String): Flow<List<ArticleResponse>> = apiService.searchNews(q)


}