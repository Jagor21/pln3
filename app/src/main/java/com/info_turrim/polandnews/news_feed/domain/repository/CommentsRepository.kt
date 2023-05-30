package com.info_turrim.polandnews.news_feed.domain.repository

import com.info_turrim.polandnews.news_feed.domain.model.Comment
import com.info_turrim.polandnews.news_feed.data.model.CommentRequest
import com.info_turrim.polandnews.news_feed.domain.model.LikeComment
import com.info_turrim.polandnews.news_feed.data.model.GetCommentsRequestParam
import kotlinx.coroutines.flow.Flow
import com.info_turrim.polandnews.base.Result

interface CommentsRepository {
    suspend fun getCommentsFlow(getCommentsRequestParam: GetCommentsRequestParam): Flow<Result<List<Comment>>>
    suspend fun getComments(newsId: Int): Result<List<Comment>>
    suspend fun requestMore(getCommentsRequestParam: GetCommentsRequestParam)
    suspend fun onLikeComment(commentId: Int): Result<LikeComment>
    suspend fun sendComment(commentRequest: CommentRequest, newsId: Int): Result<Comment>
}