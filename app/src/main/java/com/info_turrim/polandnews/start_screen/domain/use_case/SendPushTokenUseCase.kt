package com.info_turrim.polandnews.start_screen.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.start_screen.domain.StartRepository
import javax.inject.Inject

class SendPushTokenUseCase @Inject constructor(
    private val startRepository: StartRepository
) : BaseUseCase<String, Unit>() {

    override suspend fun run(param: String): Result<Unit> {
        return startRepository.sendPushNotificationToken(param)
    }
}