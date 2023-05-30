package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.news_feed.domain.repository.CommentsRepository
import com.info_turrim.polandnews.news_feed.domain.model.LikeComment
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class LikeCommentUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository
) : BaseUseCase<Int, LikeComment>() {
    override suspend fun run(param: Int): Result<LikeComment> {
        return commentsRepository.onLikeComment(param)
    }
}