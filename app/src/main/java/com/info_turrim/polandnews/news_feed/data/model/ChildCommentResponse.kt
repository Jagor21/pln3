package com.info_turrim.polandnews.news_feed.data.model

import com.google.gson.annotations.SerializedName

data class ChildCommentResponse(
    @SerializedName("author")
    val author: CommentAuthorChildResponse,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("liked")
    val liked: Int,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    @SerializedName("text")
    val text: String) {
}