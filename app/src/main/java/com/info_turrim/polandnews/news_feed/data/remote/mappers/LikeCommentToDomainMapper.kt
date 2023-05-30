package com.info_turrim.polandnews.news_feed.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.news_feed.data.model.LikeCommentResponse
import com.info_turrim.polandnews.news_feed.domain.model.LikeComment
import javax.inject.Inject

class LikeCommentToDomainMapper @Inject constructor() : Mapper<LikeCommentResponse, LikeComment> {
    override fun map(from: LikeCommentResponse) = LikeComment(
        result =  from.result
    )
}