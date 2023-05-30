package com.info_turrim.polandnews.start_screen.data.remote

import com.info_turrim.polandnews.common.model_data.CategoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StartApi {

    @GET("v1/categories/followed/")
    fun getFollowedSections(): Call<List<CategoryResponse>>

    @POST("v1/notifications/token/")
    fun addToken(@Body token: String): Call<Unit>

}