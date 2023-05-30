package com.info_turrim.polandnews.follow.data.repository.datasource

import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.common.model_domain.Category

interface FollowDataSource {
    suspend fun getFollowedCategories(): Result<List<Category>>
}