package com.info_turrim.polandnews.news_feed.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.news_feed.data.model.NewsResponse
import com.info_turrim.polandnews.news_feed.domain.model.News
import javax.inject.Inject

class NewsToDomainMapper @Inject constructor(
    private val sourceToDomainMapper: SourceToDomainMapper
) : Mapper<NewsResponse, News> {
    override fun map(from: NewsResponse): News {
        return News(
            id = from.id,
            commented = from.commented,
            createdAt = from.createdAt,
            header = from.header,
            image = from.image,
            liked = from.liked,
            likedByUser = from.likedByUser,
            shared = from.shared,
            source = sourceToDomainMapper.map(from.source),
            video = from.video,
            link = from.link,
            text = from.text,
            breaking = from.breaking,
            likeCount = from.likeCount,
            isFavourites = from.isFavourites,
            sourceUniqueId = from.sourceUniqueId
        )
    }
}