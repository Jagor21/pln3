package com.info_turrim.polandnews.news_feed.domain.model

data class ChildComment(
    val author: CommentAuthorChild,
    val createdAt: String,
    val id: Int,
    val liked: Int,
    val likedByUser: Boolean,
    val text: String
)