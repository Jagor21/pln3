package com.info_turrim.polandnews.news_feed.data.repository.datasource

import com.info_turrim.polandnews.base.BaseNetworkDataSource
import com.info_turrim.polandnews.news_feed.data.remote.NewsApi
import com.info_turrim.polandnews.news_feed.data.remote.mappers.NewsDetailsToDomainMapper
import com.info_turrim.polandnews.news_feed.domain.model.NewsDetails
import com.info_turrim.polandnews.utils.extension.bodyOrError
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class NewsDetailsDataSourceImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDetailsToDomainMapper: NewsDetailsToDomainMapper
) : BaseNetworkDataSource(), NewsDetailsDataSource {

    override suspend fun getNewsDetails(newsId: Int): Result<NewsDetails> {
        return executeWithMapper(newsDetailsToDomainMapper) {
            newsApi.getNewsDetails(newsId).bodyOrError()
        }
    }
}