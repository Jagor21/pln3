package com.info_turrim.polandnews.news_feed.domain.model

data class NewsDetails(
    val breaking: Boolean,
    val commented: Int,
    val createdAt: String?,
    val favouriteId: Int?,
    val header: String,
    val id: Int,
    val image: String,
    val isFavourites: Boolean,
    val likeCount: Int,
    val liked: Int,
    val likedByUser: Boolean,
    val link: String?,
    val shared: Int,
    val source: Source,
    val sourceUniqueId: String,
    val text: String,
    val video: String

//    val id: Int,
//    val commented: Int,
//    val createdAt: Date?,
//    val disliked: Int,
//    val header: String,
//    val image: String,
//    val text: String,
//    val liked: Int,
//    val likedByUser: Boolean = false,
//    val shared: Int,
//    val source: Source,
//    val video: String,
//    val link: String?
)


//data class Source(
//    val category: Category,
//    val city: String,
//    val country: String,
//    val id: Int,
//    val image: String,
//    val is_hidden: Boolean,
//    val original_url: Any,
//    val region: String,
//    val subtitle: Any,
//    val title: String
//)
//
//data class Category(
//    val id: Int,
//    val image: String,
//    val subtitle: Any,
//    val title: String
//)
