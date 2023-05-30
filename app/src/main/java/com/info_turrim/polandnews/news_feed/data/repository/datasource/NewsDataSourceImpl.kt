package com.info_turrim.polandnews.news_feed.data.repository.datasource

import com.info_turrim.polandnews.base.BaseNetworkDataSource
import com.info_turrim.polandnews.news_feed.data.remote.ForYouNewsApi
import com.info_turrim.polandnews.news_feed.data.remote.NewsApi
import com.info_turrim.polandnews.news_feed.data.remote.mappers.ShareNewsToDomainMapper
import com.info_turrim.polandnews.news_feed.data.remote.mappers.SourceDetailsToDomainMapper
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.common.toListMapper
import com.info_turrim.polandnews.news_feed.data.model.*
import com.info_turrim.polandnews.news_feed.data.remote.AdApi
import com.info_turrim.polandnews.news_feed.data.remote.mappers.AdToNewsDomainMapper
import com.info_turrim.polandnews.news_feed.domain.model.*
import com.info_turrim.polandnews.utils.extension.bodyOrError
import com.info_turrim.polandnews.utils.extension.mapTo
import javax.inject.Inject

class NewsDataSourceImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val forYouNewsApi: ForYouNewsApi,
    private val adApi: AdApi,
    private val shareNewsToDomainMapper: ShareNewsToDomainMapper,
    private val sourceDetailsToDomainMapper: SourceDetailsToDomainMapper,
    private val adToNewsDomainMapper: AdToNewsDomainMapper,
) : BaseNetworkDataSource(), NewsDataSource {

    override suspend fun getNews(getNewsRequestParam: GetNewsRequestParam): NewsListResponse {
        return newsApi.getNews(
            filterType = getNewsRequestParam.newsFilterType,
            country = getNewsRequestParam.country,
            region = getNewsRequestParam.region,
            city = getNewsRequestParam.city,
            categoryId = getNewsRequestParam.categoryId,
            page = getNewsRequestParam.page,
            sourceId = getNewsRequestParam.sourceId
        )
    }

    override suspend fun getForYouNews(getForYouNewsRequestParam: GetForYouNewsRequestParam): NewsListResponse {
        return forYouNewsApi.getForYouNews(
            getForYouNewsRequestParam.userId,
            getForYouNewsRequestParam.pageIndex
        )
    }

    override suspend fun shareNews(newsId: Int): Result<ShareNews> {
        return executeWithMapper(shareNewsToDomainMapper) {
            newsApi.shareNews(newsId).bodyOrError()
        }
    }

    override suspend fun getSourceDetails(sourceId: Int): Result<SourceDetails> {
        return executeWithMapper(sourceDetailsToDomainMapper) {
            newsApi.getSourceDetails(sourceId).bodyOrError()
        }
    }

    override suspend fun addToFavourite(favoriteRequest: FavoriteRequest): Result<AddToFavourite> {
        return execute {
            val result = newsApi.addToFavourite(favoriteRequest).bodyOrError()
            result.mapTo<AddToFavourite>()
        }
    }

    override suspend fun likeNews(newsId: Int): Result<LikeNews> {
        return execute {
            val result = newsApi.likeNews(newsId).bodyOrError()
            result.mapTo()
        }
    }

    override suspend fun removeFromFavourite(id: Int): Result<AddToFavourite> {
        return execute {
            val result = newsApi.removeFromFavourites(id.toString()).bodyOrError()
            result.mapTo<AddToFavourite>()
        }
    }

    override suspend fun getAd(getAdRequestParam: GetAdRequestParam): Result<List<News>> {
        return execute {
            val result = adApi.getAds(
                uuid = getAdRequestParam.uuid,
                adsQuantity = getAdRequestParam.adsQuantity
            ).bodyOrError()
            adToNewsDomainMapper.toListMapper().map(result.feed)
        }
    }
}