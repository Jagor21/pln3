package com.info_turrim.polandnews.auth.data.remote

import com.info_turrim.polandnews.auth.data.model.*
import com.info_turrim.polandnews.options.data.models.SignUpEmailRequest
import com.info_turrim.polandnews.options.data.models.SignUpProfileResponse
import com.info_turrim.polandnews.auth.data.model.GoogleSignUpRequest
import retrofit2.Call
import retrofit2.http.*

interface AuthApi {

    @POST("v1/token/refresh/")
    fun tokenRefresh(@Body tokenRequest: com.info_turrim.polandnews.auth.data.model.TokenRequest): Call<com.info_turrim.polandnews.auth.data.model.TokenResponse>

    @POST("https://api.bsystem.colistios.com/lead-postback")
    fun reportClickId(
        @Body status: String,
        @Body token: String,
        @Body click_id: String
    ): Call<Boolean>

    @POST("v1/sign-up/email/")
    fun fakeSignUp(@Body signUpBody: com.info_turrim.polandnews.auth.data.model.SignUpFakeRequest, @Query("confirmation_code") code: String): Call<SignUpProfileResponse>

    @POST("v1/sign-up/email/")
    fun signUpEmail(@Body signUpEmailRequest: SignUpEmailRequest): Call<SignUpProfileResponse>

    @POST("v1/sign-in/email/")
    fun signInEmail (@Body signInEmailRequest: SignUpEmailRequest): Call<SignUpProfileResponse>

    @POST("v1/sign-up/google/")
    fun googleSignUp (@Body googleSignUpRequest: GoogleSignUpRequest): Call<SignUpProfileResponse>

    @POST("v1/sign-in/google/")
    fun googleSignIn (@Body googleSignUpRequest: GoogleSignUpRequest): Call<SignUpProfileResponse>
}