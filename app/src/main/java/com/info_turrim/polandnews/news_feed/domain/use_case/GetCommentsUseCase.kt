package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.*
import com.info_turrim.polandnews.news_feed.domain.repository.CommentsRepository
import com.info_turrim.polandnews.news_feed.domain.model.Comment
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository
) :
    BaseUseCase<Int, List<Comment>>() {
    override suspend fun run(param: Int): Result<List<Comment>> {
        return commentsRepository.getComments(param)
    }
}