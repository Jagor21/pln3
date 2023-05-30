package com.info_turrim.polandnews.news_feed.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.news_feed.data.model.NewsDetailsResponse
import com.info_turrim.polandnews.news_feed.domain.model.NewsDetails
import javax.inject.Inject

class NewsDetailsToDomainMapper @Inject constructor(
    private val sourceToDomainMapper: SourceToDomainMapper
) : Mapper<NewsDetailsResponse, NewsDetails> {
    override fun map(from: NewsDetailsResponse) = NewsDetails(
        id = from.id,
        commented = from.commented,
        createdAt = from.createdAt,
        header = from.header,
        image = from.image,
        text = from.text,
        liked = from.liked,
        likedByUser = from.likedByUser,
        shared = from.shared,
        source = sourceToDomainMapper.map(from.source),
        video = from.video,
        link = from.link,
        isFavourites = from.isFavourites,
        favouriteId = from.favouriteId,
        breaking = from.breaking,
        likeCount = from.likeCount,
        sourceUniqueId = from.sourceUniqueId
    )
}