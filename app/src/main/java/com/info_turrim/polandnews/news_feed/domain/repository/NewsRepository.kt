package com.info_turrim.polandnews.news_feed.domain.repository

import com.info_turrim.polandnews.news_feed.data.model.FavoriteRequest
import com.info_turrim.polandnews.news_feed.data.model.GetForYouNewsRequestParam
import com.info_turrim.polandnews.news_feed.data.model.GetNewsRequestParam
import com.info_turrim.polandnews.news_feed.domain.model.*
import kotlinx.coroutines.flow.Flow
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.news_feed.data.model.GetAdRequestParam

interface NewsRepository {
    suspend fun getNews(getNewsRequestParam: GetNewsRequestParam): Flow<Result<Set<News>>>
    suspend fun requestMore(getNewsRequestParam: GetNewsRequestParam)
    suspend fun shareNews(id: Int): Result<ShareNews>
    suspend fun getSourceDetails(sourceId: Int): Result<SourceDetails>
    suspend fun addToFavorite(favoriteRequest: FavoriteRequest): Result<AddToFavourite>
    suspend fun likeNews(newsId: Int): Result<LikeNews>
    suspend fun getForYouNews(getForYouNewsRequestParam: GetForYouNewsRequestParam): Flow<Result<Set<News>>>
    suspend fun requestMoreForYou(getForYouNewsRequestParam: GetForYouNewsRequestParam)
    suspend fun removeFromFavourites(id: Int): Result<AddToFavourite>
    suspend fun getAd(getAdRequestParam: GetAdRequestParam)/*: Result<List<News>>*/
}