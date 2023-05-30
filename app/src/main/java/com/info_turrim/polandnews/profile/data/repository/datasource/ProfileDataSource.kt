package com.info_turrim.polandnews.profile.data.repository.datasource

import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.options.data.models.FavouritesNewsListResponse
import com.info_turrim.polandnews.base.Result

interface ProfileDataSource {

    suspend fun getFavoritesNews(pageIndex: Int): FavouritesNewsListResponse
    suspend fun getUserCategories(): Result<List<Category>>
}