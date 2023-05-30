package com.info_turrim.polandnews.news_feed.data.repository

import com.info_turrim.polandnews.news_feed.data.repository.datasource.NewsDetailsDataSource
import com.info_turrim.polandnews.news_feed.domain.model.NewsDetails
import com.info_turrim.polandnews.news_feed.domain.repository.NewsDetailsRepository
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class NewsDetailsRepositoryImpl @Inject constructor(
    private val newsDetailsDataSource: NewsDetailsDataSource
) : NewsDetailsRepository {

    override suspend fun getNewsDetails(newsId: Int): Result<NewsDetails> {
        return newsDetailsDataSource.getNewsDetails(newsId)
    }
}