package com.info_turrim.polandnews.news_feed.data.model

import com.google.gson.annotations.SerializedName

data class AdDataResponse(
    @SerializedName("block_show_uuid")
    val blockShowUuid: String,
    @SerializedName("confirm_url")
    val confirmUrl: String,
    @SerializedName("feed")
    val feed: List<AdResponse>,
    @SerializedName("placement_request_uuid")
    val placementRequestUuid: String
)