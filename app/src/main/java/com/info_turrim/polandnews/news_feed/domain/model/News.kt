package com.info_turrim.polandnews.news_feed.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val ad: Boolean = false,
    val breaking: Boolean,
    val commented: Int,
    val createdAt: String,
    val header: String,
    val id: Int,
    val image: String?,
    val isFavourites: Boolean,
    val likeCount: Int,
    val liked: Int,
    val likedByUser: Boolean,
    val link: String,
    val shared: Int,
    val source: Source,
    val sourceUniqueId: String,
    val text: String,
    val video: String,
    val isAd: Boolean = false,
    val confirmUrl: String = "",
    val wasConfirmed: Boolean = false
//    @Transient var isAd: Boolean = false,
//    val id: Int,
//    val commented: Int,
//    val createdAt: String?,
//    val disliked: Int,
//    val header: String,
//    val image: String?,
//    val liked: Int,
//    val likedByUser: Boolean = false,
//    val shared: Int,
//    val source: Source?,
//    val video: String,
//    val link: String,
//    val filterType: String?,
//    val text: String?
) : Parcelable {

    val mSourceTitle: String
        get() = source?.title.orEmpty()

    val mCreatedAt: String
        get() = createdAt

    val mLiked: Int
        get() = liked

    val mHeader: String
        get() = header

    val mLikedByUser: Boolean
        get() = likedByUser

    val mCommented: Int
        get() = commented
}