package com.info_turrim.polandnews.news_feed.data.repository.datasource

import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.news_feed.data.model.*
import com.info_turrim.polandnews.news_feed.domain.model.*

interface NewsDataSource {
    suspend fun getNews(getNewsRequestParam: GetNewsRequestParam): NewsListResponse
    suspend fun shareNews(newsId: Int): Result<ShareNews>
    suspend fun getSourceDetails(sourceId: Int): Result<SourceDetails>
    suspend fun addToFavourite(favoriteRequest: FavoriteRequest): Result<AddToFavourite>
    suspend fun likeNews(newsId: Int): Result<LikeNews>
    suspend fun getForYouNews(getForYouNewsRequestParam: GetForYouNewsRequestParam): NewsListResponse
    suspend fun removeFromFavourite(id: Int): Result<AddToFavourite>
    suspend fun getAd(getAdRequestParam: GetAdRequestParam): Result<List<News>>
//    suspend fun confirmAd(getAdRequestParam: GetAdRequestParam): Result<List<News>>
}