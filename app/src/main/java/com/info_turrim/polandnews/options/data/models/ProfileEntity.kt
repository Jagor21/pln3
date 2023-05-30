package com.info_turrim.polandnews.options.data.models

import com.google.gson.annotations.SerializedName

data class ProfileEntity(
    @SerializedName("city")
    val city: String,
    @SerializedName("commented_news")
    val commented_news: List<CommentedNewsResponse>,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("followers")
    val followers: List<FollowerResponse>,
    @SerializedName("following")
    val following: List<FollowingResponse>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("password")
    val password: String,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("real")
    val real: Boolean,
    @SerializedName("username")
    val username: String
)