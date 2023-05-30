package com.info_turrim.polandnews.news_feed.data.model

import com.google.gson.annotations.SerializedName

data class LikeCommentResponse(
    @SerializedName("result")
    val result: String
)