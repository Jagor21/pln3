package com.info_turrim.polandnews.start_screen.domain.use_case

import com.info_turrim.polandnews.auth.domain.AuthRepository
import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.base.Result
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<com.info_turrim.polandnews.auth.data.model.TokenRequest, com.info_turrim.polandnews.auth.data.model.TokenResponse>() {
    override suspend fun run(param: com.info_turrim.polandnews.auth.data.model.TokenRequest): Result<com.info_turrim.polandnews.auth.data.model.TokenResponse> {
        return authRepository.refreshToken(param)
    }
}