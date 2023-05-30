package com.info_turrim.polandnews.follow.data.repository.datasource

import com.info_turrim.polandnews.base.BaseNetworkDataSource
import com.info_turrim.polandnews.common.mapper.CategoryToDomainMapper
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.common.toListMapper
import com.info_turrim.polandnews.follow.data.remote.FollowApi
import com.info_turrim.polandnews.utils.extension.bodyOrError
import com.info_turrim.polandnews.base.Result
import javax.inject.Inject

class FollowDataSourceImpl @Inject constructor(
    private val followApi: FollowApi,
    private val categoryToDomainMapper: CategoryToDomainMapper
) : BaseNetworkDataSource(), FollowDataSource {

    override suspend fun getFollowedCategories(): Result<List<Category>> {
        return executeWithMapper(categoryToDomainMapper.toListMapper()) {
            followApi.getFollowedCategories().bodyOrError()
        }
    }
}