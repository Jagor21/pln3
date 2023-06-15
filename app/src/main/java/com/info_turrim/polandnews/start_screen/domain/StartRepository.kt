package com.info_turrim.polandnews.start_screen.domain

import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.start_screen.data.model.PushTokenParam
import com.info_turrim.polandnews.start_screen.data.model.PushTokenResponse

interface StartRepository {

    suspend fun getFollowedSections(): Result<List<Category>>
    suspend fun sendPushNotificationToken(tokenParam: PushTokenParam): Result<PushTokenResponse>
}