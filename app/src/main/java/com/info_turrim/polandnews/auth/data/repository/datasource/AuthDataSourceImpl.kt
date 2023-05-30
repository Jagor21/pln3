package com.info_turrim.polandnews.auth.data.repository.datasource

import com.info_turrim.polandnews.auth.data.model.*
import com.info_turrim.polandnews.auth.data.remote.AuthApi
import com.info_turrim.polandnews.base.BaseNetworkDataSource
import com.info_turrim.polandnews.options.data.models.SignUpEmailRequest
import com.info_turrim.polandnews.options.data.remote.mapper.ProfileToDomainMapper
import com.info_turrim.polandnews.options.data.remote.mapper.SignUpProfileToDomainMapper
import com.info_turrim.polandnews.options.domain.model.SignUpProfile
import com.info_turrim.polandnews.utils.extension.bodyOrError
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.auth.data.model.GoogleSignUpRequest
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApi: AuthApi,
    private val profileToDomainMapper: ProfileToDomainMapper,
    private val signUpProfileToDomainMapper: SignUpProfileToDomainMapper
) : BaseNetworkDataSource(), AuthDataSource {

    override suspend fun refreshToken(tokenRequest: TokenRequest): Result<TokenResponse> {
        return execute {
            authApi.tokenRefresh(tokenRequest).bodyOrError()
        }
    }

    override fun reportClickId(click_id: String): Boolean {
        return authApi.reportClickId(
            status = "confirm",
            token = "t4t534jtqiuj4rijllrej354535ydfkv",
            click_id = click_id
        ).bodyOrError()
    }

    override suspend fun fakeSignUp(signUpBody: SignUpFakeRequest): Result<SignUpProfile> {
        return executeWithMapper(signUpProfileToDomainMapper) {
            authApi.fakeSignUp(signUpBody, "1111").bodyOrError()
        }
    }

    override suspend fun signUpEmail(signUpEmailRequest: SignUpEmailRequest): Result<SignUpProfile> {
        return executeWithMapper(signUpProfileToDomainMapper) {
            authApi.signUpEmail(signUpEmailRequest).bodyOrError()
        }
    }

    override suspend fun signInEmail(signInEmailRequest: SignUpEmailRequest): Result<SignUpProfile> {
        return executeWithMapper(signUpProfileToDomainMapper) {
            authApi.signInEmail(signInEmailRequest).bodyOrError()
        }
    }

    override suspend fun googleSignUp(token: String): Result<SignUpProfile> {
        return executeWithMapper(signUpProfileToDomainMapper) {
            authApi.googleSignUp(
                GoogleSignUpRequest(
                    token
                )
            ).bodyOrError()
        }
    }

    override suspend fun googleSignIn(token: String): Result<SignUpProfile> {
        return executeWithMapper(signUpProfileToDomainMapper) {
            authApi.googleSignIn(
                GoogleSignUpRequest(
                    token
                )
            ).bodyOrError()
        }
    }
}