package com.info_turrim.polandnews.news_feed.data.remote

import com.google.gson.annotations.SerializedName
import com.info_turrim.polandnews.news_feed.data.model.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_NEWS_PAGE_SIZE = 10

interface ForYouNewsApi {

    @GET("news/for-you/")
    suspend fun getForYouNews(
        @Query("user_id") userId: Int,
        @Query("page_index") pageIndex: Int,
        @Query("page_size") pageSize: Int = DEFAULT_NEWS_PAGE_SIZE
    ): NewsListResponse
}

class ForYouNewsListResponse : ArrayList<ForYouNewsResponse>()

data class ForYouNewsResponse(
    @SerializedName("breaking")
    val breaking: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("header")
    val header: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("like_count")
    val likeCount: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("shared")
    val shared: Int,
    @SerializedName("source_id")
    val sourceId: Int,
    @SerializedName("source_unique_id")
    val sourceUniqueId: String,
    @SerializedName("text")
    val text: String
)