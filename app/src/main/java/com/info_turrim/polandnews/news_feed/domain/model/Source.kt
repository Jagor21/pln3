package com.info_turrim.polandnews.news_feed.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
//    val category: Category?,
    val city: String,
    val country: String,
    val id: Int,
    val image: String?,
    val isHidden: Boolean,
    val originalUrl: String?,
    val region: String,
    val subtitle: String?,
    val title: String
//    val city: String,
//    val country: String,
//    val id: Int,
//    var followedByUser: Boolean,
//    val unfollowedByUser: Boolean,
//    val image: String?,
//    val region: String,
//    val title: String,
//    val category: Category?,
//    val newsSourceId: Long?
) : Parcelable {
}