package com.info_turrim.polandnews.news_feed.domain.model

data class Comment(
    val author: CommentAuthor,
    val childComments: List<ChildComment>,
    val commented: Int,
    val createdAt: String,
    val id: Int,
    val liked: Int,
    val likedByUser: Boolean,
    val text: String
)