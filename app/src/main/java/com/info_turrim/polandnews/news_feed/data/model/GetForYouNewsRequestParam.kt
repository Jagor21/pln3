package com.info_turrim.polandnews.news_feed.data.model

data class GetForYouNewsRequestParam(
    val userId: Int,
    val pageIndex: Int,
    val pageSize: Int?
)