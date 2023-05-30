package com.info_turrim.polandnews.news_feed.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.news_feed.data.remote.ForYouNewsResponse
import com.info_turrim.polandnews.news_feed.domain.model.ForYouNews
import javax.inject.Inject

class ForYouNewsToDomainMapper @Inject constructor() : Mapper<ForYouNewsResponse, ForYouNews> {
    override fun map(from: ForYouNewsResponse) = ForYouNews(
        breaking = from.breaking,
        createdAt = from.createdAt,
        header = from.header,
        id = from.id,
        image = from.image,
        likeCount = from.likeCount,
        link = from.link,
        shared = from.shared,
        sourceId = from.sourceId,
        sourceUniqueId = from.sourceUniqueId,
        text = from.text
    )
}