package com.info_turrim.polandnews.sections.domain.use_case

import com.info_turrim.polandnews.auth.domain.AuthRepository
import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.options.domain.model.SignUpProfile
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class GoogleSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<String, SignUpProfile>() {
    override suspend fun run(param: String): Result<SignUpProfile> {
        return authRepository.googleSignIn(param)
    }
}