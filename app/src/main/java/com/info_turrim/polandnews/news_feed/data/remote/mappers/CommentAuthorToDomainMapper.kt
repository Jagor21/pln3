package com.info_turrim.polandnews.news_feed.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.news_feed.data.model.CommentAuthorResponse
import com.info_turrim.polandnews.news_feed.domain.model.CommentAuthor
import javax.inject.Inject

class CommentAuthorToDomainMapper @Inject constructor() : Mapper<CommentAuthorResponse, CommentAuthor> {
    override fun map(from: CommentAuthorResponse): CommentAuthor {
        return CommentAuthor(
            city = from.city,
            id = from.id,
            photo = from.photo,
            username = from.username
        )
    }
}