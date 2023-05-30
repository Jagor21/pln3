package com.info_turrim.polandnews.news_feed.data.model

import com.google.gson.annotations.SerializedName

data class SourceResponse(
//    @SerializedName("category")
//    val category: CategoryResponse?,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String?,
    @SerializedName("isHidden")
    val isHidden: Boolean,
    @SerializedName("original_url")
    val originalUrl: String?,
    @SerializedName("region")
    val region: String,
    @SerializedName("subtitle")
    val subtitle: String?,
    @SerializedName("title")
    val title: String
//    val city: String,
//    val country: String,
//    val id: Int,
//    var followedByUser: Boolean,
//    val unfollowedByUser: Boolean,
//    val image: String?,
//    val region: String,
//    val title: String,
//    val category: CategoryResponse?,
//    val newsSourceId: Long?
    ) {
}