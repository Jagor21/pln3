package com.info_turrim.polandnews.start_screen.domain

import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.common.model_domain.Category

interface StartRepository {

    suspend fun getFollowedSections(): Result<List<Category>>
    suspend fun sendPushNotificationToken(token: String): Result<Unit>
}