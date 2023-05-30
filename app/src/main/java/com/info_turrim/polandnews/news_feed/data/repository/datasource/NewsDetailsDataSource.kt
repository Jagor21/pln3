package com.info_turrim.polandnews.news_feed.data.repository.datasource

import com.info_turrim.polandnews.news_feed.domain.model.NewsDetails
import com.info_turrim.polandnews.base.Result

interface NewsDetailsDataSource {

    suspend fun getNewsDetails(newsId: Int): Result<NewsDetails>
}