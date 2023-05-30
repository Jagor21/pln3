package com.info_turrim.polandnews.follow.domain.repository

import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.common.model_domain.Category

interface FollowRepository {
    suspend fun getFollowedCategories(): Result<List<Category>>
}