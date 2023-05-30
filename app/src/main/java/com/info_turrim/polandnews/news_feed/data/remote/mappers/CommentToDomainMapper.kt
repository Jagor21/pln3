package com.info_turrim.polandnews.news_feed.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.common.toListMapper
import com.info_turrim.polandnews.news_feed.data.model.CommentResponse
import com.info_turrim.polandnews.news_feed.domain.model.Comment
import javax.inject.Inject

class CommentToDomainMapper @Inject constructor(
    private val commentAuthorToDomainMapper: CommentAuthorToDomainMapper,
    private val childCommentToDomainMapper: ChildCommentToDomainMapper
) : Mapper<CommentResponse, Comment> {
    override fun map(from: CommentResponse): Comment {
        return Comment(
            author = commentAuthorToDomainMapper.map(from.author),
            childComments = childCommentToDomainMapper.toListMapper().map(from.childComments),
            commented = from.commented,
            createdAt = from.createdAt,
            id = from.id,
            liked = from.liked,
            likedByUser = from.likedByUser,
            text = from.text
        )
    }
}