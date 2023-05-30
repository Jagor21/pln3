package com.info_turrim.polandnews.news_feed.domain.model

import com.info_turrim.polandnews.news_feed.data.model.AdResponse

data class AdDataItem(
    val blockShowUuid: String,
    val confirmUrl: String,
    val feed: List<AdResponse>,
    val placementRequestUuid: String
)
