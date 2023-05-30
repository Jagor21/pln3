package com.info_turrim.polandnews.news_feed.data.remote

import com.info_turrim.polandnews.news_feed.data.model.*
import retrofit2.Call
import retrofit2.http.*

private const val DEFAULT_NEWS_PAGE_SIZE = 10
private const val DEFAULT_COMMENTS_PAGE_SIZE = 25

interface NewsApi {

    @GET("v1/news/")
    suspend fun getNews(
        @Query("filter_type") filterType: String?,
        @Query("country") country: String?,
        @Query("region") region: String?,
        @Query("city") city: String?,
        @Query("category") categoryId: Int?,
        @Query("page_index") page: Int,
        @Query("page_size") pageSize: Int = DEFAULT_NEWS_PAGE_SIZE,
        @Query("source_id") sourceId: Int?
    ): NewsListResponse


    @PUT("v1/news/{news_id}/share/")
    fun shareNews(@Path("news_id") newsId: Int): Call<ShareNewsResponse>

    @GET("v1/news/{news_id}/comments/")
    suspend fun getCommentsForFlow(
        @Path("news_id") newsId: Int,
        @Query("page_index") page: Int,
        @Query("page_size") pageSize: Int = DEFAULT_COMMENTS_PAGE_SIZE
    ): CommentListResponse

    @GET("v1/news/{news_id}/comments/")
    fun getComments(
        @Path("news_id") newsId: Int,
        @Query("page_index") page: Int = 1,
        @Query("page_size") pageSize: Int = DEFAULT_COMMENTS_PAGE_SIZE
    ): Call<CommentListResponse>

    @POST("v1/comments/{comment_id}/likes/")
    fun onLikeComment(@Path("comment_id") commentId: Int): Call<LikeCommentResponse>

    @GET("v1/news/{news_id}/")
    fun getNewsDetails(@Path("news_id") newsId: Int): Call<NewsDetailsResponse>

    @GET("v1/sources/{source_id}")
    fun getSourceDetails(@Path("source_id") sourceId: Int): Call<SourceDetailsResponse>

    @POST("v1/favourites/")
    fun addToFavourite(@Body favoriteRequest: FavoriteRequest): Call<AddToFavouriteResponse>

    @DELETE("v1/favourites/{id}")
    fun removeFromFavourites(@Path("id") id: String): Call<AddToFavouriteResponse>

    @POST("v1/news/{news_id}/likes/")
    fun likeNews(@Path("news_id") newsId: Int): Call<LikeNewsResponse>

    @POST("v1/news/{news_id}/comments/")
    fun sendComment(
        @Body commentRequest: CommentRequest,
        @Path("news_id") newsId: Int
    ): Call<CommentResponse>

}