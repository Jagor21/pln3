package com.info_turrim.polandnews.options.data.remote

import com.info_turrim.polandnews.options.data.models.EditProfileRequest
import com.info_turrim.polandnews.options.data.models.EditProfileResponse
import com.info_turrim.polandnews.options.data.models.SupportRequest
import com.info_turrim.polandnews.options.data.models.SupportResponse
import com.info_turrim.polandnews.options.data.models.*
import retrofit2.Call
import retrofit2.http.*

interface OptionsApi {

    @PUT("v1/profile/")
    fun editProfile(@Body profile: EditProfileRequest): Call<EditProfileResponse>

    @POST("v1/questions/")
    fun sendQuestion(@Body supportRequest: SupportRequest): Call<SupportResponse>
}