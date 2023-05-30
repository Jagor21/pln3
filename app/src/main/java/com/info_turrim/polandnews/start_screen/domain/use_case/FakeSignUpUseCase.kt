package com.info_turrim.polandnews.start_screen.domain.use_case

import com.info_turrim.polandnews.auth.domain.AuthRepository
import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.options.domain.model.SignUpProfile
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class FakeSignUpUseCase @Inject constructor(
private val authRepository: AuthRepository
) : BaseUseCase<com.info_turrim.polandnews.auth.data.model.SignUpFakeRequest, SignUpProfile>() {

    override suspend fun run(param: com.info_turrim.polandnews.auth.data.model.SignUpFakeRequest): Result<SignUpProfile> {
        return authRepository.fakeSignUp(param)
    }
}