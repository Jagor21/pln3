package com.info_turrim.polandnews.news_feed.data.repository.datasource

import com.info_turrim.polandnews.news_feed.data.model.CommentListResponse
import com.info_turrim.polandnews.news_feed.data.model.CommentRequest
import com.info_turrim.polandnews.news_feed.data.model.GetCommentsRequestParam
import com.info_turrim.polandnews.news_feed.domain.model.Comment
import com.info_turrim.polandnews.news_feed.domain.model.LikeComment
import com.info_turrim.polandnews.base.Result

interface CommentsDataSource {

    suspend fun getCommentsForFlow(getCommentsRequestParam: GetCommentsRequestParam): CommentListResponse
    suspend fun getComments(newsId: Int): Result<List<Comment>>
    suspend fun onLikeComment(commentId: Int): Result<LikeComment>
    suspend fun sendComment(commentRequest: CommentRequest, newsId: Int): Result<Comment>
}