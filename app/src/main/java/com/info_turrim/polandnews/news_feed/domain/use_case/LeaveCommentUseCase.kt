package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.news_feed.domain.model.Comment
import com.info_turrim.polandnews.news_feed.domain.model.LeaveCommentRequestParams
import com.info_turrim.polandnews.news_feed.domain.repository.CommentsRepository
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class LeaveCommentUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository
) : BaseUseCase<LeaveCommentRequestParams, Comment>() {

    override suspend fun run(param: LeaveCommentRequestParams): Result<Comment> {
        return commentsRepository.sendComment(
            commentRequest = param.commentRequest,
            newsId = param.newsId
        )
    }
}