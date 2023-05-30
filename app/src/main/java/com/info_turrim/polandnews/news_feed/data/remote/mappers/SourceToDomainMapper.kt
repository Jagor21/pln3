package com.info_turrim.polandnews.news_feed.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.common.mapper.CategoryToDomainMapper
import com.info_turrim.polandnews.news_feed.data.model.SourceResponse
import com.info_turrim.polandnews.news_feed.domain.model.Source
import javax.inject.Inject

class SourceToDomainMapper @Inject constructor(
    private val categoryToDomainMapper: CategoryToDomainMapper
) : Mapper<SourceResponse, Source> {

    override fun map(from: SourceResponse): Source {
        return Source(
//            category = from.category?.let { categoryToDomainMapper.map(it) },
            city = from.city,
            country = from.country,
            id = from.id,
            image = from.image?.let { imageUrl ->
                if (imageUrl.contains("backend.polishnews23")) {
                    imageUrl
                } else {
                    "https://backend.polishnews23.com:443/media${imageUrl}"
                }
            },
            isHidden = from.isHidden,
            originalUrl = from.originalUrl,
            region = from.region,
            subtitle = from.subtitle,
            title = from.title
        )
    }
}