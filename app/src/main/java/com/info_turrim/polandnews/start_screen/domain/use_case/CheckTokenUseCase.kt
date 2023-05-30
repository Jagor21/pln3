package com.info_turrim.polandnews.start_screen.domain.use_case

import android.content.SharedPreferences
import com.info_turrim.polandnews.auth.domain.AuthRepository
import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.utils.extension.getAccessToken
import com.info_turrim.polandnews.utils.extension.setToken
import com.info_turrim.polandnews.base.Result
import javax.inject.Inject

class CheckTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPreferences: SharedPreferences
) : BaseUseCase<com.info_turrim.polandnews.auth.data.model.TokenRequest, Boolean>() {

    override suspend fun run(param: com.info_turrim.polandnews.auth.data.model.TokenRequest): Result<Boolean> {
        if (sharedPreferences.getAccessToken().isEmpty()) {
            return Result.Success(false)
        }
        authRepository.refreshToken(param).fold(
            onSuccess = {
                sharedPreferences.setToken(it)
            },
            onFailure = {}
        )
        return Result.Success(true)
    }
}