package com.info_turrim.polandnews.auth.data.repository

import com.info_turrim.polandnews.auth.data.repository.datasource.AuthDataSource
import com.info_turrim.polandnews.auth.domain.AuthRepository
import com.info_turrim.polandnews.options.data.models.SignUpEmailRequest
import com.info_turrim.polandnews.options.domain.model.SignUpProfile
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun refreshToken(tokenRequest: com.info_turrim.polandnews.auth.data.model.TokenRequest): Result<com.info_turrim.polandnews.auth.data.model.TokenResponse> {
        return authDataSource.refreshToken(tokenRequest)
    }

    override fun reportClickId(click_id: String): Boolean {
        return authDataSource.reportClickId(click_id)
    }

    override suspend fun fakeSignUp(signUpBody: com.info_turrim.polandnews.auth.data.model.SignUpFakeRequest): Result<SignUpProfile> {
        return authDataSource.fakeSignUp(signUpBody)
    }

    override suspend fun signUpEmail(signUpEmailRequest: SignUpEmailRequest): Result<SignUpProfile> {
        return authDataSource.signUpEmail(signUpEmailRequest)
    }

    override suspend fun signInEmail(signInEmailRequest: SignUpEmailRequest): Result<SignUpProfile> {
        return authDataSource.signInEmail(signInEmailRequest)
    }

    override suspend fun googleSignUp(token: String): Result<SignUpProfile> {
        return authDataSource.googleSignUp(token)
    }

    override suspend fun googleSignIn(token: String): Result<SignUpProfile> {
        return authDataSource.googleSignIn(token)
    }
}