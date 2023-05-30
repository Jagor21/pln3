package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.news_feed.data.model.GetCommentsRequestParam
import com.info_turrim.polandnews.news_feed.domain.repository.CommentsRepository
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class GetMoreCommentsUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository
) : BaseUseCase<GetCommentsRequestParam, Unit>() {

    override suspend fun run(param: GetCommentsRequestParam): Result<Unit> {
        return Result.Success(commentsRepository.requestMore(param))
    }
}