package com.info_turrim.polandnews.news_feed.domain

import kotlinx.coroutines.flow.Flow
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.options.domain.model.FavouritesNewsItem
import com.info_turrim.polandnews.base.Result

interface ProfileRepository {

    suspend fun getFavoritesNews(pageIndex: Int): Flow<Result<List<FavouritesNewsItem>>>
    suspend fun requestMore(pageIndex: Int)
    suspend fun getUserCategories(): Result<List<Category>>
}