package com.info_turrim.polandnews.options.data.models

import com.google.gson.annotations.SerializedName
import com.info_turrim.polandnews.news_feed.data.model.NewsResponse

data class FavouritesNewsItemResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("news")
    val news: NewsResponse
)
