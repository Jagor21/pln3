package com.info_turrim.polandnews.start_screen.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.start_screen.data.model.PushTokenParam
import com.info_turrim.polandnews.start_screen.data.model.PushTokenResponse
import com.info_turrim.polandnews.start_screen.domain.StartRepository
import javax.inject.Inject

class SendPushTokenUseCase @Inject constructor(
    private val startRepository: StartRepository
) : BaseUseCase<PushTokenParam, PushTokenResponse>() {

    override suspend fun run(param: PushTokenParam): Result<PushTokenResponse> {
        return startRepository.sendPushNotificationToken(param)
    }
}