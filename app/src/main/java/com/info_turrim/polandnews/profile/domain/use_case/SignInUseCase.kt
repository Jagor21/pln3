package com.info_turrim.polandnews.profile.domain.use_case

import com.info_turrim.polandnews.auth.domain.AuthRepository
import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.options.data.models.SignUpEmailRequest
import com.info_turrim.polandnews.options.domain.model.SignUpProfile
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<SignUpEmailRequest, SignUpProfile>() {
    override suspend fun run(param: SignUpEmailRequest): Result<SignUpProfile> {
        return authRepository.signInEmail(param)
    }
}