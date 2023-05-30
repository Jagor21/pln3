package com.info_turrim.polandnews.news_feed.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.common.mapper.CategoryToDomainMapper
import com.info_turrim.polandnews.news_feed.data.model.SourceDetailsResponse
import com.info_turrim.polandnews.news_feed.domain.model.SourceDetails
import javax.inject.Inject

class SourceDetailsToDomainMapper @Inject constructor(
    private val categoryToDomainMapper: CategoryToDomainMapper
) :
    Mapper<SourceDetailsResponse, SourceDetails> {

    override fun map(from: SourceDetailsResponse): SourceDetails {
        return SourceDetails(
            category = categoryToDomainMapper.map(from.category),
            city = from.city,
            country = from.country,
            followedByUser = from.followedByUser,
            followerCount = from.followerCount,
            id = from.id,
            image = from.image,
            isHidden = from.isHidden,
            newsCount = from.newsCount,
            originalUrl = from.originalUrl,
            region = from.region,
            title = from.title,
            unfollowedByUser = from.unfollowedByUser,
            subtitle = from.subtitle
        )
    }
}