package com.info_turrim.polandnews.options.domain.model

import com.info_turrim.polandnews.news_feed.domain.model.News

data class FavouritesNewsItem(
    val id: Int,
    val news: News
)
