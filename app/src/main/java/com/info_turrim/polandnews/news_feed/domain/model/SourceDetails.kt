package com.info_turrim.polandnews.news_feed.domain.model

import com.info_turrim.polandnews.common.model_domain.Category

data class SourceDetails(
    val category: Category,
    val city: String,
    val country: String,
    val followedByUser: Boolean,
    val followerCount: Int,
    val id: Int,
    val image: String,
    val isHidden: Boolean,
    val newsCount: Int,
    val originalUrl: String?,
    val region: String,
    val title: String,
    val unfollowedByUser: Boolean,
    val subtitle: String?
)