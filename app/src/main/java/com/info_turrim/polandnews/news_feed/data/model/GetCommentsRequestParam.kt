package com.info_turrim.polandnews.news_feed.data.model

data class GetCommentsRequestParam(
    val newsId: Int,
    val pageIndex: Int,
    val pageSize: Int?
)