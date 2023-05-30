package com.info_turrim.polandnews.follow.data.repository

import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.follow.data.repository.datasource.FollowDataSource
import com.info_turrim.polandnews.follow.domain.repository.FollowRepository
import javax.inject.Inject

class FollowRepositoryImpl @Inject constructor(
    private val followDataSource: FollowDataSource
) : FollowRepository {

    override suspend fun getFollowedCategories(): Result<List<Category>> {
        return followDataSource.getFollowedCategories()
    }
}