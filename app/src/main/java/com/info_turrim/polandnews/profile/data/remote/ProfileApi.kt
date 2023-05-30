package com.info_turrim.polandnews.profile.data.remote

import com.info_turrim.polandnews.common.model_data.CategoryResponse
import com.info_turrim.polandnews.options.data.models.FavouritesNewsListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_NEWS_PAGE_SIZE = 10

interface ProfileApi {

    @GET("v1/favourites")
    suspend fun getFavoritesNews(
        @Query("page_index") page: Int,
        @Query("page_size") pageSize: Int = DEFAULT_NEWS_PAGE_SIZE
    ): FavouritesNewsListResponse

    @GET("v1/categories/followed/")
    fun getUserCategories(): Call<List<CategoryResponse>>

//    @GET("/v1/categories/")
//    fun getUserCategories(): Call<List<CategoryResponse>>
}