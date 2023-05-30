package com.info_turrim.polandnews.profile.data.repository.datasource

import com.info_turrim.polandnews.base.BaseNetworkDataSource
import com.info_turrim.polandnews.common.mapper.CategoryToDomainMapper
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.common.toListMapper
import com.info_turrim.polandnews.options.data.models.FavouritesNewsListResponse
import com.info_turrim.polandnews.profile.data.remote.ProfileApi
import com.info_turrim.polandnews.utils.extension.bodyOrError
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class ProfileDataSourceImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val categoryToDomainMapper: CategoryToDomainMapper
) : BaseNetworkDataSource(), ProfileDataSource {

    override suspend fun getFavoritesNews(pageIndex: Int): FavouritesNewsListResponse {
        return profileApi.getFavoritesNews(pageIndex)
    }

    override suspend fun getUserCategories(): Result<List<Category>> {
        return executeWithMapper(categoryToDomainMapper.toListMapper()) {
            profileApi.getUserCategories().bodyOrError()
        }
    }
}