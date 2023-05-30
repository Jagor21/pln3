package com.info_turrim.polandnews.news_feed.domain.model

import com.info_turrim.polandnews.news_feed.data.model.CommentRequest

data class LeaveCommentRequestParams(
    val commentRequest: CommentRequest,
    val newsId: Int
)
