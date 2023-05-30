package com.info_turrim.polandnews.auth.data.repository.datasource

import com.info_turrim.polandnews.options.data.models.SignUpEmailRequest
import com.info_turrim.polandnews.options.domain.model.SignUpProfile
import com.info_turrim.polandnews.base.Result

interface AuthDataSource {
    suspend fun refreshToken(tokenRequest: com.info_turrim.polandnews.auth.data.model.TokenRequest): Result<com.info_turrim.polandnews.auth.data.model.TokenResponse>

    fun reportClickId(click_id: String): Boolean

    suspend fun fakeSignUp(signUpBody: com.info_turrim.polandnews.auth.data.model.SignUpFakeRequest): Result<SignUpProfile>
    suspend fun signUpEmail(signUpEmailRequest: SignUpEmailRequest): Result<SignUpProfile>
    suspend fun signInEmail(signInEmailRequest: SignUpEmailRequest): Result<SignUpProfile>
    suspend fun googleSignUp(token: String): Result<SignUpProfile>
    suspend fun googleSignIn(token: String): Result<SignUpProfile>
}