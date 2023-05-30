package com.info_turrim.polandnews.news_feed.data.model

import com.google.gson.annotations.SerializedName

data class CommentAuthorChildResponse(
    @SerializedName("city")
    val city: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("username")
    val username: String
)