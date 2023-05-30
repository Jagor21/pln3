package com.info_turrim.polandnews.follow.data.remote

import com.info_turrim.polandnews.common.model_data.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET

interface FollowApi {

    @GET("v1/categories/followed")
    fun getFollowedCategories(): Call<List<CategoryResponse>>
}