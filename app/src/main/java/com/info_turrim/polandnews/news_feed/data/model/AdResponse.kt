package com.info_turrim.polandnews.news_feed.data.model

import com.google.gson.annotations.SerializedName

data class AdResponse(
    @SerializedName("ad_id")
    val adId: Int,
    @SerializedName("ad_show_uuid")
    val adShowUuid: String,
    @SerializedName("click_url")
    val clickUrl: String,
    @SerializedName("confirm_url")
    val confirmUrl: String,
    @SerializedName("media_url")
    val mediaUrl: String,
    @SerializedName("title")
    val title: String
)