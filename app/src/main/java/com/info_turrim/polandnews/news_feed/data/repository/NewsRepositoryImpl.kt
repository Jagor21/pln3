package com.info_turrim.polandnews.news_feed.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.info_turrim.polandnews.MainActivity
import com.info_turrim.polandnews.ad.AdManager
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
import com.info_turrim.polandnews.utils.extension.getUUID
import com.info_turrim.polandnews.utils.extension.setUUID
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val NEWS_STARTING_PAGE_INDEX = 1

class NewsRepositoryImpl @Inject constructor(
    private val newsDataSource: NewsDataSource,
    private val newsToDomainMapper: NewsToDomainMapper,
    private val forYouNewsToDomainMapper: ForYouNewsToDomainMapper,
    private val prefs: SharedPreferences,
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

    private var adList: List<News> = listOf()

    private var uuid: String = ""

    var adIndex = 0

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
        if (!isForYouNewsEmpty) {
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
            val news = newsToDomainMapper.toListMapper().map(response).toMutableList()
            val needToShowAd = Firebase.remoteConfig.getBoolean("show_content")
            val ads = AdManager.adList
            if (needToShowAd && ads.isNotEmpty()) {
                val newsCount = news.size / 5
                var insertAdIndex = 5
                repeat(newsCount) {
                    news.add(insertAdIndex, ads[adIndex])
                    if (adIndex >= ads.size - 1) {
                        uuid = prefs.getUUID()
                        if (uuid.isEmpty()) {
                            uuid = java.util.UUID.randomUUID().toString()
                            prefs.setUUID(uuid)
                        }
                        getAd(
                            GetAdRequestParam(
                                uuid = uuid,
                                adsQuantity = 10
                            )
                        )
                        adIndex = 0
                    }
                    insertAdIndex += 6
                    adIndex++
                }
            }
            inMemoryCache.clear()
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
                val needToShowAd = Firebase.remoteConfig.getBoolean("show_content")
                val ads = AdManager.adList
                if (needToShowAd && ads.isNotEmpty()) {
                    val newsCount = news.size / 5
                    var insertAdIndex = 5
                    repeat(newsCount) {
                        news.add(insertAdIndex, ads[adIndex])
                        if (adIndex >= ads.size - 1) {
                            uuid = prefs.getUUID()
                            if (uuid.isEmpty()) {
                                uuid = java.util.UUID.randomUUID().toString()
                                prefs.setUUID(uuid)
                            }
                            getAd(
                                GetAdRequestParam(
                                    uuid = uuid,
                                    adsQuantity = 10
                                )
                            )
                            adIndex = 0
                        }
                        insertAdIndex += 6
                        adIndex++
                    }
                }
                inMemoryCache.clear()
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
//        val needToShowAd = Firebase.remoteConfig.getBoolean("show_content")
//        if (needToShowAd) {
//            adList = newsDataSource.getAd(getAdRequestParam)
        return newsDataSource.getAd(getAdRequestParam)
//        }
    }
}