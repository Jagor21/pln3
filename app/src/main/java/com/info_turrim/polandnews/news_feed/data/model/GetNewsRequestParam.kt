package com.info_turrim.polandnews.news_feed.data.model

data class GetNewsRequestParam(
    val newsFilterType: String? = null,
    val country: String? = null,
    val region: String? = null,
    val city: String? = null,
    val page: Int,
    val categoryId: Int? = null,
    val sourceId: Int? = null
)
