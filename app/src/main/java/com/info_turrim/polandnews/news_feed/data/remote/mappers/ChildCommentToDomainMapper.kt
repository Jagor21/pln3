package com.info_turrim.polandnews.news_feed.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.news_feed.data.model.ChildCommentResponse
import com.info_turrim.polandnews.news_feed.domain.model.ChildComment
import javax.inject.Inject

class ChildCommentToDomainMapper @Inject constructor(
    private val commentAuthorChildToDomainMapper: CommentAuthorChildToDomainMapper
) : Mapper<ChildCommentResponse, ChildComment> {
    override fun map(from: ChildCommentResponse): ChildComment {
        return ChildComment(
            author = commentAuthorChildToDomainMapper.map(from.author),
            createdAt = from.createdAt,
            id = from.id,
            liked = from.liked,
            likedByUser = from.likedByUser,
            text = from.text
        )
    }
}