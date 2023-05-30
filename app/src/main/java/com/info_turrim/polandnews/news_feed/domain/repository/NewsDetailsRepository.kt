package com.info_turrim.polandnews.news_feed.domain.repository

import com.info_turrim.polandnews.news_feed.domain.model.NewsDetails
import com.info_turrim.polandnews.base.Result

interface NewsDetailsRepository {

    suspend fun getNewsDetails(newsId: Int): Result<NewsDetails>
}