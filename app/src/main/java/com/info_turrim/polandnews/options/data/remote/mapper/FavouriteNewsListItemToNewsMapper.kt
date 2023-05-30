package com.info_turrim.polandnews.options.data.remote.mapper

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.options.domain.model.FavouritesNewsItem
import javax.inject.Inject

class FavouriteNewsListItemToNewsMapper @Inject constructor() : Mapper<FavouritesNewsItem, News> {
    override fun map(from: FavouritesNewsItem) = News(
        id = from.news.id,
        commented = from.news.commented,
        createdAt = from.news.createdAt,
        header = from.news.header,
        image = from.news.image,
        liked = from.news.liked,
        likedByUser = from.news.likedByUser,
        shared = from.news.shared,
        source = from.news.source,
        video = from.news.video,
        link = from.news.link,
        text = from.news.text,
        breaking = from.news.breaking,
        likeCount = from.news.likeCount,
        isFavourites = from.news.isFavourites,
        sourceUniqueId = from.news.sourceUniqueId
    )
}