package com.info_turrim.polandnews.auth.domain

import com.info_turrim.polandnews.auth.data.model.SignUpFakeRequest
import com.info_turrim.polandnews.auth.data.model.TokenRequest
import com.info_turrim.polandnews.auth.data.model.TokenResponse
import com.info_turrim.polandnews.options.data.models.SignUpEmailRequest
import com.info_turrim.polandnews.options.domain.model.SignUpProfile
import com.info_turrim.polandnews.base.Result

interface AuthRepository {

    suspend fun refreshToken(tokenRequest: TokenRequest): Result<TokenResponse>

    fun reportClickId(click_id: String): Boolean

    suspend fun fakeSignUp(signUpBody: SignUpFakeRequest): Result<SignUpProfile>

    suspend fun signUpEmail(signUpEmailRequest: SignUpEmailRequest): Result<SignUpProfile>
    suspend fun signInEmail(signInEmailRequest: SignUpEmailRequest): Result<SignUpProfile>
    suspend fun googleSignUp(token: String): Result<SignUpProfile>
    suspend fun googleSignIn(token: String): Result<SignUpProfile>
}