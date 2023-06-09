package com.info_turrim.polandnews.start_screen.data.repository.datasource

import com.info_turrim.polandnews.base.BaseNetworkDataSource
import com.info_turrim.polandnews.common.mapper.CategoryToDomainMapper
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.common.toListMapper
import com.info_turrim.polandnews.start_screen.data.remote.StartApi
import com.info_turrim.polandnews.utils.extension.bodyOrError
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.start_screen.data.model.PushTokenParam
import com.info_turrim.polandnews.start_screen.data.model.PushTokenResponse

class StartDataSourceImpl @Inject constructor(
    private val startApi: StartApi,
    private val categoryToDomainMapper: CategoryToDomainMapper
) : BaseNetworkDataSource(), StartDataSource {

    override suspend fun getFollowedSections(): Result<List<Category>> {
        return executeWithMapper(categoryToDomainMapper.toListMapper()) {
            startApi.getFollowedSections().bodyOrError()
        }
    }

    override suspend fun sendPushNotificationToken(tokenParam: PushTokenParam): Result<PushTokenResponse> {
        return execute { startApi.addToken(tokenParam).bodyOrError() }
    }

}