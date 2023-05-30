package com.info_turrim.polandnews.news_feed.data.repository.datasource

import com.info_turrim.polandnews.base.BaseNetworkDataSource
import com.info_turrim.polandnews.common.toListMapper
import com.info_turrim.polandnews.news_feed.data.model.CommentListResponse
import com.info_turrim.polandnews.news_feed.data.model.CommentRequest
import com.info_turrim.polandnews.news_feed.data.model.GetCommentsRequestParam
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.news_feed.data.remote.NewsApi
import com.info_turrim.polandnews.news_feed.data.remote.mappers.CommentToDomainMapper
import com.info_turrim.polandnews.news_feed.data.remote.mappers.LikeCommentToDomainMapper
import com.info_turrim.polandnews.news_feed.domain.model.Comment
import com.info_turrim.polandnews.news_feed.domain.model.LikeComment
import com.info_turrim.polandnews.utils.extension.bodyOrError
import javax.inject.Inject

class CommentsDataSourceImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val likeCommentToDomainMapper: LikeCommentToDomainMapper,
    private val commentToDomainMapper: CommentToDomainMapper,
) : BaseNetworkDataSource(), CommentsDataSource {

    override suspend fun getCommentsForFlow(getCommentsRequestParam: GetCommentsRequestParam): CommentListResponse {
        return newsApi.getCommentsForFlow(
            newsId = getCommentsRequestParam.newsId,
            page = getCommentsRequestParam.pageIndex
        )
    }

    override suspend fun getComments(newsId: Int): Result<List<Comment>> {
        return executeWithMapper(commentToDomainMapper.toListMapper()) {
            newsApi.getComments(newsId).bodyOrError()
        }
    }

    override suspend fun onLikeComment(commentId: Int): Result<LikeComment> {
        return executeWithMapper(likeCommentToDomainMapper) {
            newsApi.onLikeComment(commentId).bodyOrError()
        }
    }

    override suspend fun sendComment(
        commentRequest: CommentRequest,
        newsId: Int
    ): Result<Comment> {
        return executeWithMapper(commentToDomainMapper) {
            newsApi.sendComment(commentRequest, newsId).bodyOrError()
        }
    }
}