package com.info_turrim.polandnews.news_feed.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.news_feed.data.model.CommentAuthorChildResponse
import com.info_turrim.polandnews.news_feed.domain.model.CommentAuthorChild
import javax.inject.Inject

class CommentAuthorChildToDomainMapper @Inject constructor() : Mapper<CommentAuthorChildResponse, CommentAuthorChild> {
    override fun map(from: CommentAuthorChildResponse): CommentAuthorChild {
        return CommentAuthorChild(
            city = from.city,
            id = from.id,
            photo = from.photo,
            username = from.username
        )
    }
}