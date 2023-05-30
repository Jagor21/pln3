package com.info_turrim.polandnews.news_feed.domain.model

data class ForYouNews(
    val breaking: Boolean,
    val createdAt: String,
    val header: String,
    val id: Int,
    val image: String,
    val likeCount: Int,
    val link: String,
    val shared: Int,
    val sourceId: Int,
    val sourceUniqueId: String,
    val text: String

//    val commented: Int,
//    val isFavourites: Boolean,
//    val liked: Int,
//    val likedByUser: Boolean,
//    val source: Source,
//    val video: String
)
