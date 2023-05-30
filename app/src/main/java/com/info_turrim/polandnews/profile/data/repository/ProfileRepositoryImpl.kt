package com.info_turrim.polandnews.profile.data.repository

import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.common.toListMapper
import com.info_turrim.polandnews.news_feed.domain.ProfileRepository
import com.info_turrim.polandnews.options.data.remote.mapper.FavouriteNewsItemToDomainMapper
import com.info_turrim.polandnews.options.data.remote.mapper.FavouriteNewsListItemToNewsMapper
import com.info_turrim.polandnews.options.domain.model.FavouritesNewsItem
import com.info_turrim.polandnews.profile.data.repository.datasource.ProfileDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

private const val NEWS_STARTING_PAGE_INDEX = 1

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource,
    private val favouriteNewsItemToDomainMapper: FavouriteNewsItemToDomainMapper,
    private val favouriteNewsListItemToNewsMapper: FavouriteNewsListItemToNewsMapper,
): ProfileRepository {

    // keep the list of all results received
    private val inMemoryCache = mutableListOf<FavouritesNewsItem>()

    // shared flow of results, which allows us to broadcast updates so
    // the subscriber will have the latest data
    private val newsResults = MutableSharedFlow<Result<List<FavouritesNewsItem>>>(replay = 1)

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = NEWS_STARTING_PAGE_INDEX

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override suspend fun getFavoritesNews(pageIndex: Int): Flow<Result<List<FavouritesNewsItem>>> {
        lastRequestedPage = pageIndex
        inMemoryCache.clear()
        requestNews(lastRequestedPage)
        return newsResults
    }


    override suspend fun requestMore(pageIndex: Int) {
        if (isRequestInProgress) return
        val successful = requestNews(lastRequestedPage)
        if (successful) {
            lastRequestedPage++
        }
    }

    override suspend fun getUserCategories(): Result<List<Category>> {
        return profileDataSource.getUserCategories()
    }

    private suspend fun requestNews(pageIndex: Int): Boolean {
        isRequestInProgress = true
        var successful = false

        try {
            val response =
                profileDataSource.getFavoritesNews(pageIndex)
            val news = favouriteNewsItemToDomainMapper.toListMapper().map(response)

            inMemoryCache.addAll(news)
            newsResults.emit(Result.Success(inMemoryCache.toSet().toList()))
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
}