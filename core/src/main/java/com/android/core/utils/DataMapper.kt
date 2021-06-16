package com.android.core.utils

import com.android.core.data.source.local.entity.ArticleEntity
import com.android.core.data.source.local.entity.SourceEntity
import com.android.core.data.source.remote.response.ArticleResponse
import com.android.core.domain.model.Article
import com.android.core.domain.model.Source
import kotlin.collections.ArrayList

object DataMapper {
    fun mapResponsesToDomain(input: List<ArticleResponse>): List<Article> =
        input.map {
            Article(
                id = it.id,
                description = it.description,
                title = it.title,
                author = it.author,
                content = it.content,
                publishedAt = it.publishedAt,
                source = Source(it.sourceResponse?.id, it.sourceResponse!!.name),
                url = it.url,
                urlToImage = it.urlToImage,
                isFavorite = false
            )
        }

    fun mapResponsesToEntities(input: List<ArticleResponse>): List<ArticleEntity> {
        val newsList = ArrayList<ArticleEntity>()
        input.map {
            val article = ArticleEntity(
                id = it.id,
                description = it.description,
                title = it.title,
                author = it.author,
                content = it.content,
                publishedAt = it.publishedAt,
                source = SourceEntity(
                    it.sourceResponse?.id,
                    it.sourceResponse!!.name
                ),
                url = it.url,
                urlToImage = it.urlToImage,
                isFavorite = false
            )
            newsList.add(article)
        }
        return newsList
    }

    fun mapEntitiesToDomain(input: List<ArticleEntity>): List<Article> =
        input.map {
            Article(
                id = it.id,
                description = it.description,
                title = it.title,
                author = it.author,
                content = it.content,
                publishedAt = it.publishedAt,
                source = Source(it.source?.id, it.source!!.name),
                url = it.url,
                urlToImage = it.urlToImage,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Article) = ArticleEntity(
        id = input.id,
        description = input.description,
        title = input.title,
        author = input.author,
        content = input.content,
        publishedAt = input.publishedAt,
        source = SourceEntity(
            input.source?.id,
            input.source!!.name
        ),
        url = input.url,
        urlToImage = input.urlToImage,
        isFavorite = input.isFavorite
    )
}