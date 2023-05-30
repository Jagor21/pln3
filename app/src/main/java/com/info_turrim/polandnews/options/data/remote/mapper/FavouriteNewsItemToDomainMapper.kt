package com.info_turrim.polandnews.options.data.remote.mapper

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.news_feed.data.remote.mappers.NewsToDomainMapper
import com.info_turrim.polandnews.options.data.models.FavouritesNewsItemResponse
import com.info_turrim.polandnews.options.domain.model.FavouritesNewsItem
import javax.inject.Inject

class FavouriteNewsItemToDomainMapper @Inject constructor(
    private val newsToDomainMapper: NewsToDomainMapper
) :
    Mapper<FavouritesNewsItemResponse, FavouritesNewsItem> {
    override fun map(from: FavouritesNewsItemResponse) = FavouritesNewsItem(
        id = from.id,
        news = newsToDomainMapper.map(from.news)
    )
}