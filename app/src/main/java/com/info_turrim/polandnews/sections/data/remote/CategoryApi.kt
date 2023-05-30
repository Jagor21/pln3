package com.info_turrim.polandnews.sections.data.remote

import com.info_turrim.polandnews.common.model_data.CategoryResponse
import com.info_turrim.polandnews.sections.data.model.CategoryFollowResult
import retrofit2.Call
import retrofit2.http.*

interface CategoryApi {

    @GET("v1/categories/")
    fun getCategories(): Call<List<CategoryResponse>>

    @POST("v1/categories/{category_id}/follow/")
    fun subscribeForCategory(@Path("category_id") id: Int): Call<CategoryFollowResult>
}