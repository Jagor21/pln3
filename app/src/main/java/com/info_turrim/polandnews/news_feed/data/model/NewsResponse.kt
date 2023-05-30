package com.info_turrim.polandnews.news_feed.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class NewsResponse(
    @SerializedName("breaking")
    val breaking: Boolean,
    @SerializedName("commented")
    val commented: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("header")
    val header: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String?,
    @SerializedName("is_favourites")
    val isFavourites: Boolean,
    @SerializedName("like_count")
    val likeCount: Int,
    @SerializedName("liked")
    val liked: Int,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    @SerializedName("link")
    val link: String,
    @SerializedName("shared")
    val shared: Int,
    @SerializedName("source")
    val source: SourceResponse,
    @SerializedName("source_unique_id")
    val sourceUniqueId: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("video")
    val video: String
//    @Transient var isAd: Boolean = false,
//    @SerializedName("id")
//    val id: Int,
//    @SerializedName("commented")
//    val commented: Int,
//    @SerializedName("createdAt")
//    val createdAt: String?,
//    @SerializedName("disliked")
//    val disliked: Int,
//    @SerializedName("header")
//    val header: String,
//    @SerializedName("image")
//    val image: String?,
//    @SerializedName("liked")
//    val liked: Int,
//    @SerializedName("likedByUser")
//    val likedByUser: Boolean = false,
//    @SerializedName("shared")
//    val shared: Int,
//    @SerializedName("source")
//    val source: SourceResponse?,
//    @SerializedName("video")
//    val video: String,
//    @SerializedName("link")
//    val link: String,
//    @SerializedName("filterType")
//    val filterType: String?,
//    @SerializedName("text")
//    val text: String?
)