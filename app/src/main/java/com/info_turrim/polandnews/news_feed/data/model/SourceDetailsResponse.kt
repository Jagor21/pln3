package com.info_turrim.polandnews.news_feed.data.model

import com.google.gson.annotations.SerializedName
import com.info_turrim.polandnews.common.model_data.CategoryResponse

data class SourceDetailsResponse(
    @SerializedName("category")
    val category: CategoryResponse,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("followed_by_user")
    val followedByUser: Boolean,
    @SerializedName("follower_count")
    val followerCount: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    @SerializedName("news_count")
    val newsCount: Int,
    @SerializedName("original_url")
    val originalUrl: String?,
    @SerializedName("region")
    val region: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("unfollowed_by_user")
    val unfollowedByUser: Boolean,
    @SerializedName("subtitle")
    val subtitle: String?
)