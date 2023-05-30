package com.info_turrim.polandnews.news_feed.data.repository

import android.util.Log
import com.info_turrim.polandnews.common.toListMapper
import com.info_turrim.polandnews.news_feed.data.model.FavoriteRequest
import com.info_turrim.polandnews.news_feed.data.model.GetForYouNewsRequestParam
import com.info_turrim.polandnews.news_feed.data.model.GetNewsRequestParam
import com.info_turrim.polandnews.news_feed.data.remote.mappers.ForYouNewsToDomainMapper
import com.info_turrim.polandnews.news_feed.domain.model.*
import com.info_turrim.polandnews.news_feed.data.remote.mappers.NewsToDomainMapper
import com.info_turrim.polandnews.news_feed.data.repository.datasource.NewsDataSource
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.news_feed.data.model.GetAdRequestParam
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val NEWS_STARTING_PAGE_INDEX = 1

class NewsRepositoryImpl @Inject constructor(
    private val newsDataSource: NewsDataSource,
    private val newsToDomainMapper: NewsToDomainMapper,
    private val forYouNewsToDomainMapper: ForYouNewsToDomainMapper,
//    private val adManager: AdManager,
) : NewsRepository {

    // keep the list of all results received
    private val inMemoryCache = mutableSetOf<News>()
    private val inMemoryCacheForYou = mutableListOf<ForYouNews>()

    // shared flow of results, which allows us to broadcast updates so
    // the subscriber will have the latest data
    private val newsResults = MutableSharedFlow<Result<Set<News>>>(replay = 1)

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = NEWS_STARTING_PAGE_INDEX

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false
    private var isForYouNewsEmpty = false

    override suspend fun getNews(getNewsRequestParam: GetNewsRequestParam): Flow<Result<Set<News>>> {
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestNews(getNewsRequestParam.copy(page = lastRequestedPage))
        return newsResults
    }

    override suspend fun getForYouNews(getForYouNewsRequestParam: GetForYouNewsRequestParam): Flow<Result<Set<News>>> {
        lastRequestedPage = 1
        inMemoryCacheForYou.clear()
        requestForYouNews(getForYouNewsRequestParam.copy(pageIndex = lastRequestedPage))
        return newsResults
    }


    override suspend fun requestMore(getNewsRequestParam: GetNewsRequestParam) {
        if (isRequestInProgress) return
        val successful = requestNews(getNewsRequestParam.copy(page = ++lastRequestedPage))
        if (!successful) {
            lastRequestedPage--
        }
    }

    override suspend fun requestMoreForYou(getForYouNewsRequestParam: GetForYouNewsRequestParam) {
        if (isRequestInProgress) return
        if (!isForYouNewsEmpty){
            val successful =
                requestForYouNews(getForYouNewsRequestParam.copy(pageIndex = lastRequestedPage))
            if (successful) {
                lastRequestedPage++
            }
        }
    }

    override suspend fun shareNews(id: Int): Result<ShareNews> {
        return newsDataSource.shareNews(id)
    }

    override suspend fun getSourceDetails(sourceId: Int): Result<SourceDetails> {
        return newsDataSource.getSourceDetails(sourceId)
    }

    override suspend fun addToFavorite(favoriteRequest: FavoriteRequest): Result<AddToFavourite> {
        return newsDataSource.addToFavourite(favoriteRequest)
    }

    override suspend fun likeNews(newsId: Int): Result<LikeNews> {
        return newsDataSource.likeNews(newsId)
    }

    override suspend fun removeFromFavourites(id: Int): Result<AddToFavourite> {
        return newsDataSource.removeFromFavourite(id)
    }

    private suspend fun requestNews(getNewsRequestParam: GetNewsRequestParam): Boolean {
        isRequestInProgress = true
        var successful = false

        try {
            val response =
                newsDataSource.getNews(getNewsRequestParam.copy(page = lastRequestedPage))
            val news = newsToDomainMapper.toListMapper().map(response)

            inMemoryCache.addAll(news)
            newsResults.emit(Result.Success(inMemoryCache))
            successful = true
        } catch (exception: IOException) {
            newsResults.emit(Result.ErrorResult.LocalErrorResponse(exception))
        } catch (exception: HttpException) {
            newsResults.emit(
                Result.ErrorResult.NetworkErrorResponse(
                    exception.code(),
                    exception.message()
                )
            )
        }
        isRequestInProgress = false
        return successful
    }

    private suspend fun requestForYouNews(getForYouNewsRequestParam: GetForYouNewsRequestParam): Boolean {
        isRequestInProgress = true
        var successful = false

        try {
            val response =
                newsDataSource.getForYouNews(getForYouNewsRequestParam.copy(pageIndex = lastRequestedPage))
            val news = newsToDomainMapper.toListMapper().map(response).toMutableList()
            if (news.isNotEmpty()) {
                inMemoryCache.addAll(news)
                newsResults.emit(Result.Success(inMemoryCache))
                successful = true
                isForYouNewsEmpty = false
            } else {
                isForYouNewsEmpty = true
            }
        } catch (exception: IOException) {
            newsResults.emit(Result.ErrorResult.LocalErrorResponse(exception))
        } catch (exception: HttpException) {
            newsResults.emit(
                Result.ErrorResult.NetworkErrorResponse(
                    exception.code(),
                    exception.message()
                )
            )
        } catch (e: Exception) {

            Log.e("MY_TAG", "", e)
        }
        isRequestInProgress = false
        return successful
    }

    override suspend fun getAd(getAdRequestParam: GetAdRequestParam): Result<List<News>> {
        return newsDataSource.getAd(getAdRequestParam)
    }
}