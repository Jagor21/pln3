package com.info_turrim.polandnews.start_screen.data.repository

import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.start_screen.data.repository.datasource.StartDataSource
import com.info_turrim.polandnews.start_screen.domain.StartRepository
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.start_screen.data.model.PushTokenParam
import com.info_turrim.polandnews.start_screen.data.model.PushTokenResponse

class StartRepositoryImpl @Inject constructor(
    private val startDataSource: StartDataSource
) : StartRepository {

    override suspend fun getFollowedSections(): Result<List<Category>> {
        return startDataSource.getFollowedSections()
    }

    override suspend fun sendPushNotificationToken(tokenParam: PushTokenParam): Result<PushTokenResponse> {
        return startDataSource.sendPushNotificationToken(tokenParam)
    }
}